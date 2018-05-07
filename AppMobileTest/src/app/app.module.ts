import { NgModule, ErrorHandler} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Nav, IonicApp, IonicModule,IonicErrorHandler } from 'ionic-angular';
import { IonicStorageModule} from "@ionic/storage";
import { MyApp } from './app.component';

import { HttpModule } from '@angular/http';
import { AuthService } from '../providers/auth-service';

import { AboutPage } from '../pages/about/about';
import { ContactPage } from '../pages/contact/contact';
import { HomePage } from '../pages/home/home';
import { TabsPage } from '../pages/tabs/tabs';
import { MenuPage } from '../pages/menu/menu';
import { LoginPage } from '../pages/login/login';
import { SpecialPage } from '../pages/special/special';
import { WelcomePage } from '../pages/welcome/welcome';
import { ComptePage } from '../pages/compte/compte';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { AuthServiceProvider } from '../providers/auth-service/auth-service';

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
    ComptePage
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
    ComptePage
  ],
  providers: [
    StatusBar,
    SplashScreen, AuthService,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    AuthServiceProvider
  ]
})
export class AppModule {}


