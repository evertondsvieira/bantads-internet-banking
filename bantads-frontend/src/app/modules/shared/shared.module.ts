import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RealPipePipe } from './pipes/real-pipe.pipe';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { AlfabeticoDirective } from './directives/alfabetico.directive';
import { EmailDirective } from './directives/email.directive';
import { NumericoDirective } from './directives/numerico.directive';
import { AlfanumericoDirective } from './directives/alfanumerico.directive';


@NgModule({
  declarations: [
    RealPipePipe,
    AlfabeticoDirective,
    EmailDirective,
    NumericoDirective,
    AlfanumericoDirective
  ],
  imports: [
    CommonModule,
    NgxMaskDirective,
    NgxMaskPipe
  ],
  exports: [
    NgxMaskDirective,
    NgxMaskPipe,
    RealPipePipe,
    AlfabeticoDirective,
    EmailDirective,
    NumericoDirective,
    AlfanumericoDirective
  ],
  providers: [
    provideNgxMask(),
  ]
})
export class SharedModule { }
