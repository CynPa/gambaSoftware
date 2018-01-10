/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
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
/*  26:    */ public class MigracionClienteProveedorBean
/*  27:    */   extends PageController
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioMigracion servicioMigracion;
/*  32:    */   List<Empresa> listaClientesProveedorNoMigrados;
/*  33: 53 */   private List<ErrorCarga> errores = new ArrayList();
/*  34:    */   private boolean visible;
/*  35:    */   DataTable dtClienteProveedoresNoMigrado;
/*  36:    */   
/*  37:    */   public String migrarCienteProveedor(FileUploadEvent event)
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 60 */       this.errores = new ArrayList();
/*  42: 61 */       this.listaClientesProveedorNoMigrados = new ArrayList();
/*  43: 62 */       String fileName = "migracion_cliente_proveedor" + event.getFile().getFileName();
/*  44: 63 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  45: 64 */       this.servicioMigracion.migracionClientesProveedores(AppUtil.getOrganizacion().getId(), AppUtil.getSucursal().getId(), fileName, input, 4, this.listaClientesProveedorNoMigrados);
/*  46:    */       
/*  47: 66 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  48: 68 */       if (!this.listaClientesProveedorNoMigrados.isEmpty())
/*  49:    */       {
/*  50: 69 */         setVisible(true);
/*  51: 70 */         addInfoMessage(getLanguageController().getMensaje("msg_info_migracion_con_novedades"));
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55: 72 */         setVisible(false);
/*  56: 73 */         addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  57:    */       }
/*  58:    */     }
/*  59:    */     catch (AS2Exception e)
/*  60:    */     {
/*  61: 76 */       e.printStackTrace();
/*  62: 77 */       List<String> listaMensajes = e.getCodigoMensajes();
/*  63: 78 */       int i = 0;
/*  64: 79 */       for (String a : listaMensajes)
/*  65:    */       {
/*  66: 80 */         i = a.indexOf("*");
/*  67: 81 */         a.substring(0, i + 1);
/*  68: 82 */         ErrorCarga ec = new ErrorCarga();
/*  69: 83 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/*  70: 84 */         this.errores.add(ec);
/*  71:    */       }
/*  72: 86 */       for (String a : e.getMensajes())
/*  73:    */       {
/*  74: 87 */         ErrorCarga ec = new ErrorCarga();
/*  75: 88 */         ec.setError(a);
/*  76: 89 */         this.errores.add(ec);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (ExcepcionAS2 e)
/*  80:    */     {
/*  81: 93 */       e.printStackTrace();
/*  82: 94 */       ErrorCarga ec = new ErrorCarga();
/*  83: 95 */       ec.setError(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  84: 96 */       this.errores.add(ec);
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88: 99 */       e.printStackTrace();
/*  89:100 */       ErrorCarga ec = new ErrorCarga();
/*  90:101 */       ec.setError(e.getMessage());
/*  91:102 */       this.errores.add(ec);
/*  92:    */     }
/*  93:104 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getRutaPlantilla()
/*  97:    */   {
/*  98:109 */     return "/resources/plantillas/datosBase/AS2 Clientes-Proveedores.xls";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getNombrePlantilla()
/* 102:    */   {
/* 103:114 */     return "AS2 Clientes-Proveedores.xls";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<ErrorCarga> getErrores()
/* 107:    */   {
/* 108:118 */     return this.errores;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setErrores(List<ErrorCarga> errores)
/* 112:    */   {
/* 113:122 */     this.errores = errores;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<Empresa> getListaClientesProveedorNoMigrados()
/* 117:    */   {
/* 118:126 */     return this.listaClientesProveedorNoMigrados;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setListaClientesProveedorNoMigrados(List<Empresa> listaClientesProveedorNoMigrados)
/* 122:    */   {
/* 123:130 */     this.listaClientesProveedorNoMigrados = listaClientesProveedorNoMigrados;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isVisible()
/* 127:    */   {
/* 128:134 */     return this.visible;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setVisible(boolean visible)
/* 132:    */   {
/* 133:138 */     this.visible = visible;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtClienteProveedoresNoMigrado()
/* 137:    */   {
/* 138:142 */     return this.dtClienteProveedoresNoMigrado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtClienteProveedoresNoMigrado(DataTable dtClienteProveedoresNoMigrado)
/* 142:    */   {
/* 143:146 */     this.dtClienteProveedoresNoMigrado = dtClienteProveedoresNoMigrado;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionClienteProveedorBean
 * JD-Core Version:    0.7.0.1
 */