import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import { LoadingController } from 'ionic-angular';

@Injectable()
export class CommonProvider {
  public loader: any;
  apiUrl = 'http://localhost/ProjectIngerop/PHP-Slim-Restful/api/';
  constructor(public loadingCtrl:LoadingController,public http:Http){
    console.log('Hello CommonProvider Provider');
  }
  
  presentLoading(){
	  this.loader = this.loadingCtrl.create({content: "Please wait ..."})
	  this.loader.present();
  }

  closeLoading(){
	this.loader.dismiss();
  }
  
  AllUsers(){
	return new Promise((resolve,reject) => {
	  this.http.get(this.apiUrl+"users").subscribe(res =>{resolve(res.json());
		},err => {
		  reject(err);
		});
	 });
	}
}