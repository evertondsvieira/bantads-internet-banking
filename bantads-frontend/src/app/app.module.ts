import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { MaskComponent } from './components/mask/mask.component';
import { ClientModule } from './modules/client/client.module';
import { AuthModule } from './modules/auth/auth.module';
import { AccountModule } from './modules/account/account.module';
import { SharedModule } from './modules/shared/shared.module';
import { ManagerModule } from './modules/manager/manager.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatIconModule } from '@angular/material/icon';
import { AdminModule } from './modules/admin/admin.module';
import { ModalModule } from './components/modal/modal.module';
import { AuthInterceptor } from './services/auth/auth.interceptor';

@NgModule({
  declarations: [AppComponent, NavBarComponent, MaskComponent],
  imports: [
    BrowserModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    MatIconModule,
    NgbModule,
    AppRoutingModule,
    ClientModule,
    ManagerModule,
    AdminModule,
    AuthModule,
    AccountModule,
    SharedModule,
    ModalModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
