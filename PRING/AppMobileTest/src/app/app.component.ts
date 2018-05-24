import { Component,ViewChild } from '@angular/core';
import { Nav,Platform,MenuController,App} from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HomePage } from '../pages/home/home';
import { AboutPage } from '../pages/about/about';
import { ContactPage } from '../pages/contact/contact';
import { WelcomePage } from '../pages/welcome/welcome';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;
  rootPage:any = WelcomePage;
  //public userDetails: any;
  pages:Array<{title: string, component: any,icon:string}>;
  items:Array<{icon:string,title:string}>;
  
  constructor(public platform: Platform,
					public statusBar: StatusBar, 
					public splashScreen:SplashScreen,
					public menuCtrl:MenuController,
					public app:App){
    this.initializeApp();
  	this.pages = [
	{ title: 'Home', component: HomePage,icon:'home' },
	{ title: 'Créer un formulaire', component: AboutPage,icon:'document'},
	{ title: 'Contact', component: ContactPage,icon:'contacts'}];
	
	this.items=[{title:'Mes Docs',icon:'folder'},
		{title:'Partage avec moi',icon:'logo-buffer'},
		{title:'Boîte de réception',icon:'cube'},
		{title:'Parametre',icon:'hammer'}];
	/*const data = JSON.parse(localStorage.getItem("userData"));
    this.userDetails = data.userData;*/
  }
  initializeApp(){
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
  
  backToWelcome() {
    const root = this.app.getRootNav();
    root.popToRoot();
  }

  logoutClicked(){
    localStorage.clear();
    setTimeout(() => this.backToWelcome(), 1000);
  }
}
