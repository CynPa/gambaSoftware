/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.ventas.procesos.ErrorCarga;
/*  13:    */ import java.io.BufferedInputStream;
/*  14:    */ import java.io.InputStream;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.event.FileUploadEvent;
/*  22:    */ import org.primefaces.model.UploadedFile;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class MigracionProductoBean
/*  27:    */   extends PageController
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -7213077706907154319L;
/*  30:    */   @EJB
/*  31:    */   private ServicioMigracion servicioMigracion;
/*  32:    */   List<Producto> listaProductoNoMigrado;
/*  33:    */   DataTable dtProductoNoMigrado;
/*  34: 60 */   private List<ErrorCarga> errores = new ArrayList();
/*  35:    */   private boolean visible;
/*  36:    */   
/*  37:    */   public String migrarProducto(FileUploadEvent event)
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 69 */       String fileName = "migracion_producto_" + event.getFile().getFileName();
/*  42: 70 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  43: 71 */       this.listaProductoNoMigrado = new ArrayList();
/*  44: 72 */       this.errores = new ArrayList();
/*  45: 73 */       this.servicioMigracion.migracionProductos(AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), fileName, input, 4, this.listaProductoNoMigrado);
/*  46: 75 */       if (!this.listaProductoNoMigrado.isEmpty())
/*  47:    */       {
/*  48: 76 */         setVisible(true);
/*  49: 77 */         addInfoMessage(getLanguageController().getMensaje("msg_info_migracion_con_novedades"));
/*  50:    */       }
/*  51:    */       else
/*  52:    */       {
/*  53: 79 */         setVisible(false);
/*  54: 80 */         addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  55:    */       }
/*  56:    */     }
/*  57:    */     catch (AS2Exception e)
/*  58:    */     {
/*  59: 84 */       e.printStackTrace();
/*  60: 85 */       List<String> listaMensajes = e.getCodigoMensajes();
/*  61: 86 */       int i = 0;
/*  62: 87 */       for (String a : listaMensajes)
/*  63:    */       {
/*  64: 88 */         i = a.indexOf("*");
/*  65: 89 */         a.substring(0, i + 1);
/*  66: 90 */         ErrorCarga ec = new ErrorCarga();
/*  67: 91 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/*  68: 92 */         this.errores.add(ec);
/*  69:    */       }
/*  70: 94 */       for (String a : e.getMensajes())
/*  71:    */       {
/*  72: 95 */         ErrorCarga ec = new ErrorCarga();
/*  73: 96 */         ec.setError(a);
/*  74: 97 */         this.errores.add(ec);
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (ExcepcionAS2 e)
/*  78:    */     {
/*  79:101 */       e.printStackTrace();
/*  80:102 */       ErrorCarga ec = new ErrorCarga();
/*  81:103 */       ec.setError(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  82:104 */       this.errores.add(ec);
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:107 */       e.printStackTrace();
/*  87:108 */       ErrorCarga ec = new ErrorCarga();
/*  88:109 */       ec.setError(e.getMessage());
/*  89:110 */       this.errores.add(ec);
/*  90:    */     }
/*  91:112 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getRutaPlantilla()
/*  95:    */   {
/*  96:117 */     return "/resources/plantillas/inventario/AS2 Migracion Productos.xls";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getNombrePlantilla()
/* 100:    */   {
/* 101:122 */     return "AS2 Migracion Productos.xls";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<Producto> getListaProductoNoMigrado()
/* 105:    */   {
/* 106:126 */     return this.listaProductoNoMigrado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setListaProductoNoMigrado(List<Producto> listaProductosNoMigrados)
/* 110:    */   {
/* 111:130 */     this.listaProductoNoMigrado = listaProductosNoMigrados;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public DataTable getDtProductoNoMigrado()
/* 115:    */   {
/* 116:134 */     return this.dtProductoNoMigrado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setDtProductoNoMigrado(DataTable dtProductoNoMigrado)
/* 120:    */   {
/* 121:138 */     this.dtProductoNoMigrado = dtProductoNoMigrado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isVisible()
/* 125:    */   {
/* 126:142 */     return this.visible;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setVisible(boolean visible)
/* 130:    */   {
/* 131:146 */     this.visible = visible;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<ErrorCarga> getErrores()
/* 135:    */   {
/* 136:150 */     return this.errores;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setErrores(List<ErrorCarga> errores)
/* 140:    */   {
/* 141:154 */     this.errores = errores;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionProductoBean
 * JD-Core Version:    0.7.0.1
 */