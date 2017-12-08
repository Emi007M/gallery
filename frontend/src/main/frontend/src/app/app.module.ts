import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { ImageService } from './image.service';
import { HttpClientModule } from '@angular/common/http';
//import {DomSanitizer} from '@angular/platform-browser';



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule
  ],
  providers: [ ImageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
