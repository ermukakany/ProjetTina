import {Component } from '@angular/core';
import {NavController,ViewController, NavParams,App} from 'ionic-angular';
import {ComptePage} from '../compte/compte';

@Component({
  selector: 'page-popover',templateUrl:'popover.html'})
  
export class PopoverPage {
  rootPage:any;
  constructor(public navCtrl: NavController,public viewCtrl: ViewController,  public navParams: NavParams,public app:App) {
  }

   addusers(){
      this.navCtrl.push(ComptePage);
      this.viewCtrl.dismiss();
   }
   
   switchoff(){
	  localStorage.clear();
	  setTimeout(() => this.backToWelcome(),1000);
	  this.viewCtrl.dismiss();
    }
	
   backToWelcome() {
	  const root = this.app.getRootNav();
	  root.popToRoot();
	}
	
   close(){
      this.viewCtrl.dismiss();
    }
}
