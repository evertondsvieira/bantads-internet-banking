import { Directive, HostListener, ElementRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[alfanumerico]',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: AlfanumericoDirective,
    multi: true
  }]
})
export class AlfanumericoDirective implements ControlValueAccessor{
  onChange: any;
  onTouched: any;
  
  constructor(private el: ElementRef) { }

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
    // expressão regular
    valor = valor.replace(/[^a-zA-ZÀ-ÖØ-öø-ÿ\s0-9'\-]/g, '');
    this.writeValue(this.toTitleCase(valor));
    this.onChange(this.toTitleCase(valor));
  }

  private toTitleCase(str: string): string {
    return str.replace(/\b\w/g, (match: string) => match.toUpperCase());
  }
}