import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';

import { ClientModule } from './modules/client/client.module'
import { AuthModule } from './modules/auth/auth.module';
import { AccountModule } from './modules/account/account.module';
import { SharedModule } from './modules/shared/shared.module';
import { CommonModule } from '@angular/common';
import { BrowserModule, platformBrowser } from '@angular/platform-browser';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    CommonModule,
    ClientModule,
    AuthModule,
    AccountModule,
    SharedModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

platformBrowser().bootstrapModule(AppModule);
