def elems = _1.split()[4..-2]
def result = ""
def validate = "func (m *options) Validate() error {\n"
validate += "    err := &multierror.Error{ErrorFormat: util.MultierrFormat()}\n\n"
for (int i = 0; i<elems.size(); i+=2) {
  result +="\n"
  result +="func With" + elems[i].capitalize() + "("+elems[i] + " " + elems[i+1] + ") Option {\n"
  result +="    return func(opts *options) {\n"
  result +="        opts."+elems[i]+" = " + elems[i] + "\n"
  result +="    }\n}"

  validate += "    if m."+elems[i]+" == nil {\n"
  validate += "        err = multierror.Append(err, errors.New(\"With" + elems[i].capitalize() + " option is required\"))\n"
  validate += "    }\n\n"
}
validate += "    return err.ErrorOrNil()\n}"
return result + "\n" + validate
