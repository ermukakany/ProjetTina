import { Component,ViewChild  } from '@angular/core';
import {NavController, NavParams } from 'ionic-angular';
import { Nav,Platform } from 'ionic-angular';
import { TabsPage } from '../tabs/tabs';

export interface PageInterface {
  title: string;
  pageName: string;
  tabComponent?: any;
  index?: number;
  icon: string;
}
@Component({
  selector: 'page-menu',
  templateUrl: 'menu.html',
})

export class MenuPage {

	rootPage = TabsPage;
	@ViewChild(Nav) nav: Nav;
 
	pages: PageInterface[] = [
		{ title: 'Home', pageName:'TabsPage', tabComponent:'HomePage',index: 0,icon: 'home'},
		{ title: 'About', pageName:'TabsPage', tabComponent:'AboutPage',index: 1, icon: 'about'},
		{ title: 'Contact', pageName:'TabsPage', tabComponent:'ContactPage',index: 2, icon: 'contact' },
		{ title: 'Special', pageName:'SpecialPage', icon: 'shuffle' },
	];
	
  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  /*ionViewDidLoad() {
    console.log('ionViewDidLoad MenuPage');
  }*/
 
 openPage1(page: PageInterface) {
    let params = {};
 
    // The index is equal to the order of our tabs inside tabs.ts
    if (page.index) {
      params = { tabIndex: page.index };
    }
 
    // The active child nav is our Tabs Navigation
    if (this.nav.getActiveChildNav() && page.index != undefined) {
      this.nav.getActiveChildNav().select(page.index);
    } else {
      // Tabs are not active, so reset the root page 
      // In this case: moving to or from SpecialPage
      this.nav.setRoot(page.pageName,params);
    }
  }
 
  isActive(page: PageInterface) {
    // Again the Tabs Navigation
    let childNav = this.nav.getActiveChildNav();
 
    if (childNav) {
      if (childNav.getSelected() && childNav.getSelected().root === page.tabComponent) {
        return 'primary';
      }
      return;
    }
 
    // Fallback needed when there is no active childnav (tabs not active)
    if (this.nav.getActive() && this.nav.getActive().name === page.pageName) {
      return 'primary';
    }
    return;
  }
}