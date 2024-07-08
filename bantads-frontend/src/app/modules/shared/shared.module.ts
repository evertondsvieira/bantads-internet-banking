import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RealPipePipe } from './pipes/real-pipe.pipe';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { AlfabeticoDirective } from './directives/alfabetico.directive';
import { EmailDirective } from './directives/email.directive';
import { NumericoDirective } from './directives/numerico.directive';
import { AlfanumericoDirective } from './directives/alfanumerico.directive';
import { CpfPipe } from './pipes';
import { PhonePipe } from './pipes/phone.pipe';
import { CpfDirective } from './directives/cpf.directive';
import { DynamicCpfNameDirective } from './directives/dynamic-cpf-name.directive';

@NgModule({
  declarations: [
    RealPipePipe,
    AlfabeticoDirective,
    EmailDirective,
    NumericoDirective,
    AlfanumericoDirective,
    CpfPipe,
    PhonePipe,
    CpfDirective,
    DynamicCpfNameDirective
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
    CpfPipe,
    PhonePipe,
    AlfabeticoDirective,
    EmailDirective,
    NumericoDirective,
    AlfanumericoDirective,
    CpfDirective,
    DynamicCpfNameDirective
  ],
  providers: [
    provideNgxMask(),
  ]
})
export class SharedModule { }
