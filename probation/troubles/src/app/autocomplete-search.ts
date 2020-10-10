import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { HttpClient } from '@angular/common/http';

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
  options: string[] = ['One', 'Two', 'Three'];
  filteredOptions: Observable<string[]>;
  searchContent: string
  name: string

  constructor(private http: HttpClient) { }

  private search(): void {
    if(this.searchContent === undefined){
      this.searchContent = '';
    }
    const filterValue = this.searchContent.toLowerCase();
    let res = this.options.filter(option => option.toLowerCase().indexOf(filterValue) != -1);
    if(res.length === 0){
      res = [this.searchContent];
    }
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => res)
    );
  }

  ngOnInit() {
    this.search();
    this.name='Login Microsoft';
  }

  onSearchButtonClick(){
    console.log(this.searchContent);
    this.name = this.name + '1';
    this.search();
  }

  onLogin(){

  }
}

/**  Copyright 2020 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at http://angular.io/license */
