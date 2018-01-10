/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DimensionContable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ import javax.persistence.Column;
/*  6:   */ import javax.persistence.Entity;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.Table;
/* 11:   */ import javax.persistence.TableGenerator;
/* 12:   */ 
/* 13:   */ @Entity
/* 14:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_movimiento_inventario")
/* 15:   */ public class DetalleInterfazContableProcesoConsumoBodega
/* 16:   */   extends DetalleInterfazContableProceso
/* 17:   */ {
/* 18:   */   @Id
/* 19:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 20:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 21:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 22:   */   private Integer idDetalleInterfazContableProceso;
/* 23:   */   
/* 24:   */   public DetalleInterfazContableProcesoConsumoBodega() {}
/* 25:   */   
/* 26:   */   public DetalleInterfazContableProcesoConsumoBodega(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer bodega, String nombreBodega, Integer destinoCosto, String nombreDestinoCosto, Integer idProyecto, String nombreProyecto, String descripcion, BigDecimal valor)
/* 27:   */   {
/* 28:60 */     setDocumento(documento);
/* 29:61 */     setNombreDocumento(nombreDocumento);
/* 30:62 */     setSucursal(sucursal);
/* 31:63 */     setNombreSucursal(nombreSucursal);
/* 32:64 */     setCategoriaProducto(categoriaProducto);
/* 33:65 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/* 34:66 */     setSubcategoriaProducto(subcategoriaProducto);
/* 35:67 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/* 36:68 */     setProducto(producto);
/* 37:69 */     setNombreProducto(nombreProducto);
/* 38:70 */     setBodega(bodega);
/* 39:71 */     setNombreBodega(nombreBodega);
/* 40:72 */     setDestinoCosto(destinoCosto);
/* 41:73 */     setNombreDestinoCosto(nombreDestinoCosto);
/* 42:74 */     setDescripcion(descripcion);
/* 43:75 */     setValor(valor);
/* 44:76 */     DimensionContable proyecto = new DimensionContable();
/* 45:77 */     if (idProyecto != null)
/* 46:   */     {
/* 47:78 */       proyecto.setIdDimensionContable(idProyecto.intValue());
/* 48:79 */       proyecto.setNombre(nombreProyecto);
/* 49:80 */       setDimensionContable5(proyecto);
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoConsumoBodega
 * JD-Core Version:    0.7.0.1
 */