import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RealPipePipe } from './pipes/real-pipe.pipe';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';


@NgModule({
  declarations: [
    RealPipePipe
  ],
  imports: [
    CommonModule,
    NgxMaskDirective,
    NgxMaskPipe
  ],
  exports: [
    NgxMaskDirective,
    NgxMaskPipe,
    RealPipePipe
  ],
  providers: [
    provideNgxMask(),
  ]
})
export class SharedModule { }
