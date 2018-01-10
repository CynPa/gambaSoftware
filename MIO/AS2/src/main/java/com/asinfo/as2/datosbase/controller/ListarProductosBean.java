/*  1:   */ package com.asinfo.as2.datosbase.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.PageControllerAS2;
/*  4:   */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import javax.annotation.PostConstruct;
/*  7:   */ import javax.faces.bean.ManagedBean;
/*  8:   */ import javax.faces.bean.ManagedProperty;
/*  9:   */ import javax.faces.bean.ViewScoped;
/* 10:   */ import org.primefaces.context.RequestContext;
/* 11:   */ 
/* 12:   */ @ManagedBean
/* 13:   */ @ViewScoped
/* 14:   */ public class ListarProductosBean
/* 15:   */   extends PageControllerAS2
/* 16:   */ {
/* 17:   */   private static final long serialVersionUID = -5229752920439210529L;
/* 18:   */   @ManagedProperty("#{listaProductoBean}")
/* 19:   */   private ListaProductoBean listaProductoBean;
/* 20:   */   
/* 21:   */   @PostConstruct
/* 22:   */   public void init()
/* 23:   */   {
/* 24:46 */     RequestContext.getCurrentInstance().execute("dglModalProducto.show();");
/* 25:47 */     RequestContext.getCurrentInstance().update(":form:panelListado");
/* 26:48 */     System.out.println("<<<<<<------->>>>");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String editar()
/* 30:   */   {
/* 31:54 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String guardar()
/* 35:   */   {
/* 36:60 */     return null;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String eliminar()
/* 40:   */   {
/* 41:66 */     return null;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String limpiar()
/* 45:   */   {
/* 46:72 */     return null;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String cargarDatos()
/* 50:   */   {
/* 51:77 */     return null;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public ListaProductoBean getListaProductoBean()
/* 55:   */   {
/* 56:81 */     return this.listaProductoBean;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 60:   */   {
/* 61:86 */     this.listaProductoBean = listaProductoBean;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ListarProductosBean
 * JD-Core Version:    0.7.0.1
 */