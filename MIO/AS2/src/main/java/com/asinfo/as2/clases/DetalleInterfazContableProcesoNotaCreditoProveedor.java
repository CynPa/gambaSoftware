/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.GeneratedValue;
/*  7:   */ import javax.persistence.GenerationType;
/*  8:   */ import javax.persistence.Id;
/*  9:   */ import javax.persistence.Table;
/* 10:   */ import javax.persistence.TableGenerator;
/* 11:   */ 
/* 12:   */ @Entity
/* 13:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_nota_credito_proveedor")
/* 14:   */ public class DetalleInterfazContableProcesoNotaCreditoProveedor
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 20:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 21:   */   private Integer idDetalleInterfazContableProceso;
/* 22:   */   
/* 23:   */   public DetalleInterfazContableProcesoNotaCreditoProveedor() {}
/* 24:   */   
/* 25:   */   public DetalleInterfazContableProcesoNotaCreditoProveedor(Integer documento, Integer sucursal, Integer categoriaEmpresa, Integer proveedor, Integer categoriaProducto, Integer subcategoriaProducto, Integer producto, Integer motivoNotaCreditoProveedor, Integer impuesto, String descripcion, BigDecimal valor)
/* 26:   */   {
/* 27:53 */     setDocumento(documento);
/* 28:54 */     setSucursal(sucursal);
/* 29:55 */     setCategoriaEmpresa(categoriaEmpresa);
/* 30:56 */     setProveedor(proveedor);
/* 31:57 */     setCategoriaProducto(categoriaProducto);
/* 32:58 */     setSubcategoriaProducto(subcategoriaProducto);
/* 33:59 */     setProducto(producto);
/* 34:60 */     setMotivoNotaCreditoProveedor(motivoNotaCreditoProveedor);
/* 35:61 */     setImpuesto(impuesto);
/* 36:62 */     setDescripcion(descripcion);
/* 37:63 */     setValor(valor);
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoNotaCreditoProveedor
 * JD-Core Version:    0.7.0.1
 */