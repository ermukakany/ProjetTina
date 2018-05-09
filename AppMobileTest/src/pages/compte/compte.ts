import { Component } from '@angular/core';
import {NavController, NavParams } from 'ionic-angular';

import { AuthServiceProvider} from '../../providers/auth-service/auth-service';
import {AboutPage} from '../about/about';
import { LoginPage } from '../login/login';

@Component({selector: 'page-compte',templateUrl: 'compte.html'})

export class ComptePage {
	responseData:any;
	userData={"username":"","password":"","email":"","name":""};

  constructor(public navCtrl: NavController, public navParams: NavParams, public authService :AuthServiceProvider) {}

	signup(){
      this.authService.postData(this.userData,"signup").then((result) => {
      this.responseData = result;

		console.log(this.responseData);
		localStorage.setItem('userData',JSON.stringify(this.responseData));
		this.navCtrl.push(AboutPage);
      
    }, (err) => {
      // Error log
    });

  }

  login(){
    //Login page link
    this.navCtrl.push(LoginPage);
  }
}
