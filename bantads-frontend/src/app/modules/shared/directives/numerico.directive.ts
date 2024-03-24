import { Directive, HostListener, ElementRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[numerico]',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: NumericoDirective,
    multi: true
  }]
})
export class NumericoDirective implements ControlValueAccessor {
  onChange: any;
  onTouched: any;

  constructor(private el: ElementRef) {}

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  writeValue(value: any): void {
    if (value !== undefined) {
      this.el.nativeElement.value = value;
    }
  }

  @HostListener('input', ['$event']) onInput($event: any) {
    let valor = $event.target.value;
    // expressão regular para aceitar apenas números
    valor = valor.replace(/[^0-9]/g, '');
    this.writeValue(valor);
    this.onChange(valor);
  }
}