import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms'; 
import { ClientModule } from './modules/client/client.module'
import { AuthModule } from './modules/auth/auth.module';
import { AccountModule } from './modules/account/account.module';
import { SharedModule } from './modules/shared/shared.module';
import { CommonModule } from '@angular/common';
import { BrowserModule, platformBrowser } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ManagerModule } from './modules/manager/manager.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { MaskComponent } from './components/mask/mask.component';
import { MatIconModule } from '@angular/material/icon';
import { AdminModule } from './modules/admin/admin.module';
import { ModalModule } from './components/modal/modal.module';

@NgModule({
  declarations: [AppComponent, NavBarComponent, MaskComponent],
  imports: [
    MatIconModule,
    BrowserModule,
    CommonModule,
    ClientModule,
    ManagerModule,
    AdminModule,
    AuthModule,
    AccountModule,
    SharedModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    ModalModule,
    FormsModule
  ],
  providers: [HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule { }

platformBrowser().bootstrapModule(AppModule);
