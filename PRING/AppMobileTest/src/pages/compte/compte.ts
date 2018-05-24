import {Component } from '@angular/core';
import {NavController,NavParams,ToastController,LoadingController } from 'ionic-angular';

import {AuthServiceProvider} from '../../providers/auth-service/auth-service';
import {AboutPage} from '../about/about';
import {LoginPage } from '../login/login';

@Component({selector: 'page-compte',templateUrl: 'compte.html'})

export class ComptePage{
	public dataSet: any;
	responseData:any;
	
	selected :boolean=true;
	userData={"username":"", "password":"","email":"","name":""};
	statutData={"nom":"","slug":"","niveau":""};
	strings=[{'nom':'Administrateur','slug':'admin','niveau':4},
	{'nom':'Ressources humaines','slug':'rh','niveau':3},
	{'nom':'Assistante','slug':'assist','niveau':2},
	{'nom':'Directeur','slug':'dg','niveau':1}
	];
	//strings:Array<{nom:string,slug:string,niveau:number}>=[];
	//selected:string;
	constructor(public navCtrl: NavController, public navParams: NavParams, public authService :AuthServiceProvider,private toastCtrl:ToastController,private loadg:LoadingController) {
		//const data = JSON.parse(localStorage.getItem('statutData'));
		//this.responseData = data.statutData;
		//this.signup();		
		//this.selectItem()
		
	}
	signup(){
		if(this.userData.username && this.userData.password && this.userData.email && this.userData.name){
			//Api connections
			this.authService.postData(this.userData,'signup').then((result)=>{
			this.responseData = result;
			if(this.responseData.userData){
				console.log(this.responseData);
				localStorage.setItem('userData',JSON.stringify(this.responseData))
				 this.navCtrl.push(AboutPage);
			}
			
			else{
			this.presentToast("Please give valid username and password");
			}

			}, (err) => {
			//Connection failed message
			});
			this.selectItem();
		}
		else{console.log("Give valid information.");}
		
	}
	selectItem(){
		if(this.strings[0]){
		for(let i=0;i<this.strings.length;++i){ 
		//Api connection
		this.authService.postData(this.strings[i],'statut').then((result)=>{
		this.responseData = result;
		
		if(this.responseData){
		console.log(this.responseData);
		localStorage.setItem('statutData',JSON.stringify(this.responseData))
		}
	
	else{
	 this.presentToast("Please give valid username and password");
	}
		},(err)=> {
		//Connection failed message
		});}
		
		
		}
		
		else if(this.strings[1]){
		for(let i=0;i<this.strings.length;++i){ 
		//Api connection
		this.authService.postData(this.strings[i-1],'statut').then((result)=>{
		this.responseData = result;
		
		if(this.responseData){
		console.log(this.responseData);
		localStorage.setItem('statutData',JSON.stringify(this.responseData))
		}
	
	else{
	 this.presentToast("Please give valid username and password");
	}
		},(err)=> {
		//Connection failed message
		});}
		
		
		}
		
		else if(this.strings[2]){
		for(let i=0;i<this.strings.length;++i){ 
		//Api connection
		this.authService.postData(this.strings[i-2],'statut').then((result)=>{
		this.responseData = result;
		
		if(this.responseData){
		console.log(this.responseData);
		localStorage.setItem('statutData',JSON.stringify(this.responseData))
		}
	
	else{
	 this.presentToast("Please give valid username and password");
	}
		},(err)=> {
		//Connection failed message
		});}
		
		
		}
		
		else if(this.strings[3]){
		for(let i=0;i<this.strings.length;++i){ 
		//Api connection
		this.authService.postData(this.strings[i-3],'statut').then((result)=>{
		this.responseData = result;
		
		if(this.responseData){
		console.log(this.responseData);
		localStorage.setItem('statutData',JSON.stringify(this.responseData))
		}
	
	else{
	 this.presentToast("Please give valid username and password");
	}
		},(err)=> {
		//Connection failed message
		});}
		
		
		}
		
			/*	
			if else(this.strings[1]){
				//Api connection
				this.authService.postData(this.strings[1],'statut').then((result)=>{
				this.responseData = result;
				console.log(this.responseData);
				localStorage.setItem('statutData',JSON.stringify(this.responseData))
				},(err)=> {
				//Connection failed message
				});
				//return this.strings[1];
			}
			else if(this.strings[2]){
				//Api connection
				this.authService.postData(this.strings[2],'statut').then((result)=>{
				this.responseData = result;
				console.log(this.responseData);
				localStorage.setItem('statutData',JSON.stringify(this.responseData))
				},(err)=> {
				//Connection failed message
				});
				//return this.strings[2];
			}
			else if(this.strings[3]){
				//Api connection
				this.authService.postData(this.strings[2],'statut').then((result)=>{
				this.responseData = result;
				console.log(this.responseData);
				localStorage.setItem('statutData',JSON.stringify(this.responseData))
				},(err)=> {
				//Connection failed message
				});
				
				//return this.strings[3];
			}*/
		
		else{console.log("Give valid statut information.");}			
	}
	
	login() {
		this.navCtrl.push(LoginPage);
	}
	
	presentToast(msg) {
		let toast = this.toastCtrl.create({
			message: msg,
			duration: 2000
		});
		toast.present();
	}
	
	PresentLoadingText() {
		let loading = this.loadg.create({duration:1000});
		loading.present();
	}

}
