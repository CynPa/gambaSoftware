/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum VariableProcesoEnum
/*  4:   */ {
/*  5:21 */   DOCUMENTO("Documento"),  SUCURSAL("Sucursal"),  CATEGORIA_EMPRESA("Categoria Empresa"),  CLIENTE("Cliente"),  PROVEEDOR("Proveedor"),  CATEGORIA_PRODUCTO("Categorï¿½a Producto"),  SUBCATEGORIA_PRODUCTO("Subcategorï¿½a Producto"),  PRODUCTO("Producto"),  BODEGA("Bodega"),  CANAL("Canal"),  SUBCLIENTE("Subcliente"),  SUBPROVEEDOR("Subproveedor"),  ZONA("Zona"),  IMPUESTO("Impuesto"),  MOTIVO_NOTA_CREDITO_CLIENTE("Motivo Nota Credito Cliente"),  MOTIVO_NOTA_CREDITO_PROVEEDOR("Motivo Nota Credito Proveedor"),  MOTIVO_AJUSTE_INVENTARIO("Motivo Ajuste Inventario"),  MOTIVO_BAJA_ACTIVO("Motivo baja activo"),  CATEGORIA_ACTIVO("Categoria Activo"),  SUBCATEGORIA_ACTIVO("Subategoria Activo"),  ACTIVO_FIJO("Activo Fijo"),  EMPLEADO("Empleado"),  RUBRO("Rubro"),  CENTRO_TRABAJO("Centro de trabajo"),  CENTRO_CONSUMO("Centro consumo"),  CONCEPTO_CONTABLE("Concepto Contable"),  DEPARTAMENTO("Departamento"),  DESTINO_COSTO("Destino de costo"),  BANCO("Banco"),  VENTA("VENTA"),  INGRESO_CONTRATO_VENTA("Ingreso Contrato Venta"),  TIPO_AMORTIZACION("Tipo Amortizacion"),  CATEGORIA_IMPUESTO("Categoria Impuesto");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private VariableProcesoEnum(String nombre)
/* 10:   */   {
/* 11:58 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:67 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.VariableProcesoEnum
 * JD-Core Version:    0.7.0.1
 */