import { Component } from '@angular/core';
import { NavController,MenuController,App} from 'ionic-angular';

@Component({selector: 'page-home',templateUrl: 'home.html'})

export class HomePage {
	rootPage:any;

	constructor(public navCtrl: NavController,public menuCtrl: MenuController, public app:App) {}

	backToWelcome() {
	const root = this.app.getRootNav();
	root.popToRoot();
	}
	
	logoutClicked(){
		localStorage.clear();
		this.menuCtrl.close();
		setTimeout(() => this.backToWelcome(), 1000);
	}
}
