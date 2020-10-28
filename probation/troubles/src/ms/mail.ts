import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';

import { MailResponse, MailValue, AttachmentResponse } from './mail-response';

@Injectable()
export class MSMail {

  constructor(private http: HttpClient) {
  }

  public search(token: string, searchContent: string, res: MailValue[]): void {
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
    this.http.get<MailResponse>(url, { headers: headers }).subscribe(
      ms => ms.value.map(
        m => {
          const url = 'https://graph.microsoft.com/v1.0/me/messages/' + m.id + '/attachments';
          this.http.get<AttachmentResponse>(url, { headers: headers }).subscribe(
            as => {
              as.value.map(
                a => {
                  const toBeReplaced = 'cid:' + a.contentId;
                  console.log(m.subject + ">>>>" + toBeReplaced)
                  m.body.content = m.body.content.replace(toBeReplaced, 'data:image;base64,' + a.contentBytes);
                  
                });
              res[i++] = m;
            })
        }));
  }
}
