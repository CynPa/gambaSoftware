/*  1:   */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.ProductoDao;
/*  4:   */ import com.asinfo.as2.entities.Atributo;
/*  5:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  6:   */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*  7:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  8:   */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  9:   */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteProductoAtributo;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioReporteProductoAtributoImpl
/* 16:   */   implements ServicioReporteProductoAtributo
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private transient ProductoDao productoDao;
/* 20:   */   
/* 21:   */   public List obtenerProductoAtributo(CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, ConjuntoAtributo conjuntoAtributo, int idOrganizacion)
/* 22:   */     throws ExcepcionAS2Inventario
/* 23:   */   {
/* 24:49 */     return this.productoDao.obtenerProductoCategoriaAtributo(categoriaProducto, subcategoriaProducto, atributo, conjuntoAtributo, idOrganizacion);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteProductoAtributoImpl
 * JD-Core Version:    0.7.0.1
 */