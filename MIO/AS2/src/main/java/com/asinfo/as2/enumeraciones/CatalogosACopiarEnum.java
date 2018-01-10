/*   1:    */ package com.asinfo.as2.enumeraciones;
/*   2:    */ 
/*   3:    */ public enum CatalogosACopiarEnum
/*   4:    */ {
/*   5: 21 */   SUCURSAL("Sucursal", true),  TIPO_IDENTIFICACION("Tipo Identificacion", true),  ROL("Rol", true),  TEMA("Tema", true),  USUARIO("Usuario", true),  MODULO("Modulo", true),  CONFIGURACION("Configuracion", true),  IDIOMA("Idioma", true),  USUARIO_SUCURSAL("Usuario sucursal", true),  ACCION("Accion", true),  PERMISO("Permiso", true),  FORMA_PAGO("Forma Pago", true),  SECUENCIA("Secuencia", true),  TIPO_ASIENTO("Tipo asiento", true),  TIPO_COMPROBANTE_SRI("Tipo Comprobante SRI", true),  DOCUMENTO("Documento", true),  CREDITO_TRIBUTARIO("Credito Tributario", true),  CONCEPTO_RETENCION("Concepto Retencion", true),  CONDICION_PAGO("Condicion de Pago", true),  UNIDAD("Unidad", true),  MONEDA("Moneda", true),  IMPUESTO("Impuestos", true),  PAIS("Pais", true),  TIPO_CUENTA_BANCARIA("Tipo Cuenta Bancaria", true),  BODEGA("Bodega", true),  LISTA_PRECIOS("Lista de Precios", true),  ZONA("Zona", true),  CATEGORIA_EMPRESA("Categoria Empresa", true),  DOCUMENTO_CONTABILIZACION("Documento Contabilizacion", true),  DOCUMENTO_VARIABLE_PROCESO("Documento Variable Proceso", true),  FILTRO_PRODUCTO("Filtro Producto", true),  SUB_CATEGORIA_PRODUCTO("Subcategoria Producto", true),  BANCOS("Bancos", true),  HORAS_EXTRAS("HorasExtras", true),  ESTADO_CIVIL("Estado Civil", true),  REPORTEADOR("Reporteador", true);
/*   6:    */   
/*   7:    */   private String nombre;
/*   8:    */   private boolean indicadorObligatorio;
/*   9:    */   
/*  10:    */   private CatalogosACopiarEnum(String nombre, boolean indicadorObligatorio)
/*  11:    */   {
/*  12: 68 */     this.nombre = nombre;
/*  13: 69 */     this.indicadorObligatorio = indicadorObligatorio;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public String getNombre()
/*  17:    */   {
/*  18: 80 */     return this.nombre;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setNombre(String nombre)
/*  22:    */   {
/*  23: 90 */     this.nombre = nombre;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isIndicadorObligatorio()
/*  27:    */   {
/*  28: 99 */     return this.indicadorObligatorio;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setIndicadorObligatorio(boolean indicadorObligatorio)
/*  32:    */   {
/*  33:109 */     this.indicadorObligatorio = indicadorObligatorio;
/*  34:    */   }
/*  35:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.CatalogosACopiarEnum
 * JD-Core Version:    0.7.0.1
 */