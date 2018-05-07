import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';



@Injectable()
export class AuthServiceProvider {

  constructor(public http: HttpClient) {
    console.log('Hello AuthServiceProvider Provider');
  }

}
