import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

import { MSAuth } from '../ms/auth';
import { MSMail } from '../ms/mail';
import { MailValue } from '../ms/mail-response';

/**
 * @title Highlight the first autocomplete option
 */
@Component({
  selector: 'search',
  templateUrl: 'search.html',
  styleUrls: ['search.css'],
})
export class ContentSearch implements OnInit {
  myControl = new FormControl();
  filteredOptions: Observable<MailValue[]>;
  searchContent: string;
  loginDisabled: boolean;
  logoutDisabled: boolean;

  constructor(public auth: MSAuth, private mail: MSMail, private sanitizer: DomSanitizer) {
  }

  private search(): void {
    const res = [];
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(() => res)
    );

    this.mail.search(this.auth.getToken(), this.searchContent, res);
  }

  x(m: MailValue): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(m.body.content);
  }

  ngOnInit() {
    this.search();
    this.loginDisabled = this.auth.getUserName() === null;
    if (!this.loginDisabled) {
      this.logoutDisabled = true;
    }
  }

  onSearchButtonClick() {
    console.log(this.searchContent);
    this.search();
  }

  onLogin() {
    console.log("log in in search ...")
    this.auth.onLogin();
  }

  onLogout() {
    this.loginDisabled = false;
  }
}

/**  Copyright 2020 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at http://angular.io/license */
