import{Component} from '@angular/core';
import{NavController,PopoverController} from 'ionic-angular';
import{CommonProvider } from '../../providers/common/common';
import{PopoverPage } from '../popover/popover';
import{PopuserPage } from '../popuser/popuser';
import{ComptePage} from '../compte/compte';

@Component({selector:'page-contact',templateUrl:'contact.html'})

export class ContactPage {
 users:any =[];
 rootPage:any;
  constructor(public popoverCtrl:PopoverController,public navCtrl: NavController,public common:CommonProvider){  
	const data=JSON.parse(localStorage.getItem("userData"));
    this.users=data.userData;
	
  }
	presentPopover(event){
	   let popover = this.popoverCtrl.create(PopoverPage)
		  popover.present({
			 ev: event
		  });
	} 
	adduser(){
      this.navCtrl.push(ComptePage);
   }
   
   onClickUser(event){
	   let poplist = this.popoverCtrl.create(PopuserPage)
		  poplist.present({
			 ev: event
		  });
   }
}