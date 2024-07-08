import { Directive, HostListener, ElementRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, AbstractControl, ValidationErrors, Validator } from '@angular/forms';

@Directive({
  selector: '[cpfValidator]',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: CpfDirective,
    multi: true
  }, {
    provide: NG_VALIDATORS,
    useExisting: CpfDirective,
    multi: true
  }]
})
export class CpfDirective implements ControlValueAccessor, Validator {
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
    valor = valor.replace(/\D/g, ''); // Remove qualquer caractere não numérico
    this.writeValue(valor);
    this.onChange(valor);
  }

  validate(control: AbstractControl): ValidationErrors | null {
    const cpf = control.value;
    if (cpf && !this.isValidCPF(cpf)) {
      return { 'cpfInvalid': true };
    }
    return null;
  }

  private isValidCPF(cpf: string): boolean {
    cpf = cpf.replace(/\D/g, ''); // Remove qualquer caractere não numérico
    if (cpf.length !== 11 || /^(.)\1+$/.test(cpf)) {
      return false;
    }

    let soma = 0;
    let resto;
    for (let i = 1; i <= 9; i++) {
      soma += parseInt(cpf.substring(i - 1, i), 10) * (11 - i);
    }
    resto = (soma * 10) % 11;
    if ((resto === 10) || (resto === 11)) resto = 0;
    if (resto !== parseInt(cpf.substring(9, 10), 10)) return false;

    soma = 0;
    for (let i = 1; i <= 10; i++) {
      soma += parseInt(cpf.substring(i - 1, i), 10) * (12 - i);
    }
    resto = (soma * 10) % 11;
    if ((resto === 10) || (resto === 11)) resto = 0;
    if (resto !== parseInt(cpf.substring(10, 11), 10)) return false;

    return true;
  }
}
