import { Router, ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable()
export class MSAuth {
  private token = {};

  constructor(private route: ActivatedRoute, private router: Router) {

    //http://localhost:4200/en-us/graph/graph-explorer#access_token=a
    this.route.fragment.subscribe((fragment: string) => {
      if (fragment === null || fragment === undefined)
        return;
      const params: string[] = fragment.split('&').filter(i => i.length > 0);

      for (const p of params) {
        if (p.length < 1)
          continue;
        const keyValue: string[] = p.split('=');
        this.token[keyValue[0]] = keyValue[1];
      }
      this.token["start"] = Date.now();
      console.log("My hash fragment is here => ", this.token);
    });
  }

  onLogin() {
    console.log("onLogin in auth ... ");
    let url = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?response_type=token";
    const params = [
      { key: "scope", value: "openid profile User.Read Mail.ReadWrite" },
      { key: "client_id", value: "de8bc8b5-d9f9-48b1-a8ad-b748da725064" },
      { key: "client_info", value: "1" },
      { key: "client-request-id", value: "e72204a3-4e85-4cf7-9bcb-82140a92de5e" },
      { key: "x-client-SKU", value: "MSAL.JS" },
      { key: "x-client-Ver", value: "1.2.1" },
      { key: "redirect_uri", value: "https%3A%2F%2Fdeveloper.microsoft.com%2Fen-us%2Fgraph%2Fgraph-explorer" },
      { key: "state", value: "8de8f5b3-ce4d-47b5-a858-91f8247ef14d" },
      { key: "nonce", value: "41011bf6-3ccd-4f30-8857-2a9c72ea6ce5" },
      { key: "login_hint", value: "Simon.Wang%40refinitiv.com" },
      { key: "login_req", value: "c75f000c-465b-406b-98ba-e3bfcebab85a" },
      { key: "domain_req", value: "71ad2f62-61e2-44fc-9e85-86c2827f6de9" },
      { key: "domain_hint", value: "organizations" },
      { key: "mkt", value: "en-US" },
      { key: "prompt", value: "none" },
      { key: "response_mode", value: "fragment" },
    ];
    for (const po of params) {
      url += "&" + po["key"] + "=" + po["value"];
    }
    window.location.href = url;
  }

  getUserName(): string {
    try {
      const res = jwt_decode(this.token["access_token"]);
      return res["name"];
    }
    catch (Error) {
      return null;
    }
  }

  getToken(): string {
    try {
      const start = this.token["start"];
      const expires_in = Number.parseInt(this.token["expires_in"]);
      if ((Date.now() - start) / 1000 > expires_in - 100) {
        this.onLogin();
      }
    }
    catch (Error) {
      return null;
    }
    return this.token["access_token"]
  }
}
