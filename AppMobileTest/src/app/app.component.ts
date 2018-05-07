import { BrowserModule } from '@angular/platform-browser';
import { Component, ViewChild } from '@angular/core';
import { Nav,Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';


import { TabsPage } from '../pages/tabs/tabs';
import { HomePage } from '../pages/home/home';
import { AboutPage } from '../pages/about/about';
import { ContactPage } from '../pages/contact/contact';
import { MenuPage } from '../pages/menu/menu';
import { LoginPage } from '../pages/login/login';
import { SpecialPage } from '../pages/special/special';
import { WelcomePage } from '../pages/welcome/welcome';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;
  rootPage:any = WelcomePage;
  pages: Array<{title: string, component: any}>;
  
  constructor(public platform: Platform, public statusBar: StatusBar, public splashScreen: SplashScreen) {
    this.initializeApp();
  	this.pages = [
    	{title : 'Mon profile',component:TabsPage},
    	{ title: 'Home', component: HomePage },
    	{ title: 'About', component: AboutPage},
    	{ title: 'Contact', component: ContactPage }];
    
  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  openPage(page) {
    // Reset the content nav to have just this page
    // we wouldn't want the back button to show in this scenario
    this.nav.setRoot(page.component);
  }
}
