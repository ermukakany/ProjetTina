import { NgModule, ErrorHandler} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp,IonicModule,IonicErrorHandler } from 'ionic-angular';
import { IonicStorageModule} from "@ionic/storage";
import { MyApp} from './app.component';

import {HttpModule } from '@angular/http';
import {AuthServiceProvider} from '../providers/auth-service/auth-service';

import { AboutPage } from '../pages/about/about';
import { ContactPage } from '../pages/contact/contact';
import { HomePage } from '../pages/home/home';
import { TabsPage } from '../pages/tabs/tabs';
import { MenuPage } from '../pages/menu/menu';
import { LoginPage } from '../pages/login/login';
import { SpecialPage } from '../pages/special/special';
import { WelcomePage } from '../pages/welcome/welcome';
import { ComptePage } from '../pages/compte/compte';
import { PopoverPage } from '../pages/popover/popover';
import { PopuserPage } from '../pages/popuser/popuser';


import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { CommonProvider } from '../providers/common/common';

@NgModule({
  declarations: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    TabsPage,
	  MenuPage,
	  LoginPage,
	  SpecialPage,
    WelcomePage,
    ComptePage,
	PopoverPage,
	PopuserPage
  ],
  imports: [
    BrowserModule, HttpModule,
    IonicModule.forRoot(MyApp),
    IonicStorageModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    TabsPage,
  	MenuPage,
  	LoginPage,
  	SpecialPage,
    WelcomePage,
    ComptePage,
	PopoverPage,
	PopuserPage
  ],
  providers: [
    StatusBar,
    SplashScreen,AuthServiceProvider,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    CommonProvider
  ]
})
export class AppModule {}
