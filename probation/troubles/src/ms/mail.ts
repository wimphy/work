import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';

import { MailResponse } from './mail-response';

@Injectable()
export class MSMail {

  constructor(private http: HttpClient) {
  }

  public search(token: string, searchContent: string, res: string[]): void {
    if (token === undefined || token.length < 1) {
      return;
    }

    let i: number = res.length;
    let searchMails = "$filter=importance eq 'high'";
    if (searchContent !== undefined && searchContent.length > 0) {
      searchMails = '$search = "' + searchContent + '"';
    }
    const url = 'https://graph.microsoft.com/v1.0/me/messages?' + searchMails;
    const headers = new HttpHeaders({ 'Authorization': 'Bearer ' + token });
    this.http.get<MailResponse>(url, { headers: headers }).pipe(map(resp => {
      console.log(resp);
      resp.value.map(m => {
        res[i++] = m.subject;
      });

      return [resp];
    })).subscribe();
  }
}
