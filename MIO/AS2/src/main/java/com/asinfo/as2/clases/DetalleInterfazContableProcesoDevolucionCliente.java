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
/* 13:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_nota_credito_cliente")
/* 14:   */ public class DetalleInterfazContableProcesoDevolucionCliente
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 20:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 21:   */   private Integer idDetalleInterfazContableProceso;
/* 22:   */   
/* 23:   */   public DetalleInterfazContableProcesoDevolucionCliente() {}
/* 24:   */   
/* 25:   */   public DetalleInterfazContableProcesoDevolucionCliente(Integer documento, Integer sucursal, Integer categoriaEmpresa, Integer cliente, Integer categoriaProducto, Integer subcategoriaProducto, Integer producto, Integer subempresa, Integer impuesto, String descripcion, BigDecimal valor)
/* 26:   */   {
/* 27:52 */     setDocumento(documento);
/* 28:53 */     setSucursal(sucursal);
/* 29:54 */     setCategoriaEmpresa(categoriaEmpresa);
/* 30:55 */     setCliente(cliente);
/* 31:56 */     setCategoriaProducto(categoriaProducto);
/* 32:57 */     setSubcategoriaProducto(subcategoriaProducto);
/* 33:58 */     setProducto(producto);
/* 34:59 */     setDescripcion(descripcion);
/* 35:60 */     setValor(valor);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoDevolucionCliente
 * JD-Core Version:    0.7.0.1
 */