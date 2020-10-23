import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { MailResponse } from './mail-response';

/**
 * @title Highlight the first autocomplete option
 */
@Component({
  selector: 'autocomplete-search',
  templateUrl: 'autocomplete-search.html',
  styleUrls: ['autocomplete-search.css'],
})
export class AutocompleteSearch implements OnInit {
  myControl = new FormControl();
  filteredOptions: Observable<string[]>;
  searchContent: string;
  name: string;
  token = {};

  constructor(private http: HttpClient,
    private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.name = params['user'];
    });
    //http://localhost:4200/en-us/graph/graph-explorer#access_token=a
    this.route.fragment.subscribe((fragment: string) => {
      if (fragment === null || fragment === undefined)
        return;
      const params: string[] = fragment.split('&').filter(i => i.length > 0);
      console.log("My hash fragment is here => ", this.token);

      for (const p of params) {
        if (p.length < 1)
          continue;
        const keyValue: string[] = p.split('=');
        this.token[keyValue[0]] = keyValue[1];
      }
      //this.token = fragment.substring(13);//access_token=
      console.log("My hash fragment is here => ", this.token);
    });
  }

  private search(): void {
    if (this.searchContent === undefined) {
      this.searchContent = '';
    }
    let res = [];
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(() => res)
    );

    if (this.token["access_token"] === undefined) {
      return;
    }

    let i: number = res.length;
    let searchMails = "$filter=importance eq 'high'";
    if (this.searchContent !== undefined && this.searchContent.length > 0) {
      searchMails = '$search = "' + this.searchContent + '"';
    }
    const url = 'https://graph.microsoft.com/v1.0/me/messages?' + searchMails;
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + this.token["access_token"] });
    this.http.get<MailResponse>(url, { headers: headers }).pipe(map(resp => {
      console.log(resp);
      resp.value.map(m => {
        res[i++] = m.subject;
      });

      return [resp];
    })).subscribe();
  }

  ngOnInit() {
    this.search();
    this.name = 'Login Microsoft';
  }

  onSearchButtonClick() {
    console.log(this.searchContent);
    this.search();
  }

  onLogin() {
    let url = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?response_type=token&scope=openid%20profile%20User.Read%20Mail.ReadWrite&client_id=de8bc8b5-d9f9-48b1-a8ad-b748da725064&redirect_uri=https%3A%2F%2Fdeveloper.microsoft.com%2Fen-us%2Fgraph%2Fgraph-explorer&state=8de8f5b3-ce4d-47b5-a858-91f8247ef14d&nonce=41011bf6-3ccd-4f30-8857-2a9c72ea6ce5&client_info=1&x-client-SKU=MSAL.JS&x-client-Ver=1.2.1&login_hint=Simon.Wang%40refinitiv.com&login_req=c75f000c-465b-406b-98ba-e3bfcebab85a&domain_req=71ad2f62-61e2-44fc-9e85-86c2827f6de9&domain_hint=organizations&mkt=en-US&client-request-id=e72204a3-4e85-4cf7-9bcb-82140a92de5e&prompt=none&response_mode=fragment";

  }
}

/**  Copyright 2020 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at http://angular.io/license */
