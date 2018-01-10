/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ProcesoContabilizacionEnum
/*  4:   */ {
/*  5:21 */   COSTO_VENTAS("Costo de ventas", false),  INVENTARIO_PRODUCTO("Inventario Producto", false),  CXC_CLIENTE("CXC Cliente", false),  VENTAS("Ventas", false),  IMPUESTO_VENTAS("Impuesto en ventas", false),  DESCUENTO_VENTAS("Descuento en Ventas", false),  MOTIVO_AJUSTE_INVENTARIO("Motivo Ajuste Inventario", true),  CXP_PROVEEDOR("CXP Proveedor", false),  SUELDOS_POR_PAGAR_EMPLEADO("Sueldos por pagar Empleado", false),  INGRESOS_EMPLEADO("Ingresos Empleado", false),  EGRESOS_EMPLEADO("Egresos Empleado", false),  PROVISION_EMPLEADO("Provision Empleado", false),  CUENTA_PAGOS("Cuenta Pagos", false),  IMPUESTO_COMPRAS("Impuesto en compras", false),  MERCADERIA_POR_RECIBIR("Mercaderia por Recibir", false),  CONCEPTO_CONTABLE("Concepto contable", false),  ANTICIPO_PROVEEDOR("Anticipo Proveedor", false),  ANTICIPO_CLIENTE("Anticipo Cliente", false),  DEVOLUCION_VENTAS("Devolucion en Ventas", false),  DESCUENTO_COMPRAS("Descuento en Compras", false),  DEVOLUCION_COMPRAS("Devolucion en Compras", false),  COSTO_CONSUMO("Costo Consumo", false),  GASTO_PROTESTO("Gasto Protesto Cheque", false),  CXC_PROTESTO("CXC Protesto Cheque", false),  DEPRECIACION("Depreciación", false),  COSTO_GASTO_DEPRECIACION("Costo / Gasto Depreciación", false),  CUENTA_ORDEN_IMPUESTO_RENNTA("Cuenta de orden Impuesto a la Renta", false),  IMPUESTO_DIFERIDO("Impuesto diferido", false),  INGRESOS_CONTRATO_VENTA("Ingresos Contrato Venta", false),  ACTIVO_FIJO("Activo Fijo", false),  MOTIVO_BAJA_ACTIVO_FIJO("Motivo baja Activo fijo", false),  PRODUCCION_EN_PROCESO("Produccion en Proceso", false),  GASTO_RETENCION_ASUMIDA("Gasto Retencion Asumida", false),  DIFERENCIA_MOVIMIENTO("Diferencia Movimientos", false),  AMORTIZACION("Amortizacion", false),  GASTO_AMORTIZACION("Gasto Amortizacion", false),  DESCUENTO_IMPUESTO_COMPRAS("Descuento Impuesto en compras", false),  ICE("ICE", false),  SERVICIOS_GASTOS("Servicios Gastos", false),  PROVISION_GASTOS("Provisión Gastos", false),  DESCUENTO_IMPUESTO_VENTAS("Descuento impuesto en ventas", false),  DIFERENCIA_ASIENTO_IMPORTACION("Diferencia Importacion vs  presupuesto", false),  COSTO_FLETE("Costo Flete", false);
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private boolean indicadorMotivoAjusteInventario;
/*  9:   */   
/* 10:   */   private ProcesoContabilizacionEnum(String nombre, boolean indicadorMotivoAjusteInventario)
/* 11:   */   {
/* 12:71 */     this.nombre = nombre;
/* 13:72 */     this.indicadorMotivoAjusteInventario = indicadorMotivoAjusteInventario;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getNombre()
/* 17:   */   {
/* 18:81 */     return this.nombre;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isIndicadorMotivoAjusteInventario()
/* 22:   */   {
/* 23:85 */     return this.indicadorMotivoAjusteInventario;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum
 * JD-Core Version:    0.7.0.1
 */