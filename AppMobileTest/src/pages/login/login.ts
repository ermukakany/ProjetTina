import { Component} from '@angular/core';
import {NavController, NavParams,ToastController} from 'ionic-angular';

import { AuthServiceProvider} from '../../providers/auth-service/auth-service';
import { TabsPage } from '../tabs/tabs';

@Component({
	selector : 'page-login',
	templateUrl : 'login.html',
})
	
export class LoginPage {
	
  resposeData : any;
  userData = {"username":"", "password":""};

  constructor(public navCtrl: NavController, public navParams: NavParams,public authService :AuthServiceProvider,private toastCtrl:ToastController){}

  /*ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }*/
  
  login(){

	if(this.userData.username && this.userData.password){
		this.authService.postData(this.userData, "login").then((result) =>{
		this.resposeData = result;
		console.log(this.resposeData);
	if(this.resposeData.userData){
		localStorage.setItem('userData', JSON.stringify(this.resposeData) )
		this.navCtrl.push(TabsPage);
		}
	else{
		this.presentToast("Please give valid username and password");
		}

    }, (err) => {
      //Connection failed message
    });
   }
   else{
    this.presentToast("Give username and password");
   }
  
  }


  presentToast(msg) {
    let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000
    });
    toast.present();

	}
}
