/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.Herramienta;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class HerramientaBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<Herramienta> servicioHerramienta;
/*  32:    */   private Herramienta herramienta;
/*  33:    */   private LazyDataModel<Herramienta> listaHerramienta;
/*  34:    */   private DataTable dtHerramienta;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 51 */     this.listaHerramienta = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<Herramienta> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 58 */         List<Herramienta> lista = new ArrayList();
/*  46: 59 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 61 */         lista = HerramientaBean.this.servicioHerramienta.obtenerListaPorPagina(Herramienta.class, startIndex, pageSize, sortField, ordenar, filters);
/*  49: 62 */         HerramientaBean.this.listaHerramienta.setRowCount(HerramientaBean.this.servicioHerramienta.contarPorCriterio(Herramienta.class, filters));
/*  50:    */         
/*  51: 64 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58: 77 */     if (getHerramienta().getId() > 0) {
/*  59: 78 */       setEditado(true);
/*  60:    */     } else {
/*  61: 80 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  62:    */     }
/*  63: 82 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String guardar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70: 93 */       this.servicioHerramienta.guardarValidar(this.herramienta);
/*  71: 94 */       limpiar();
/*  72: 95 */       setEditado(false);
/*  73: 96 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74:    */     }
/*  75:    */     catch (AS2Exception e)
/*  76:    */     {
/*  77: 98 */       JsfUtil.addErrorMessage(e, "");
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:100 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  82:101 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84:103 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String limpiar()
/*  88:    */   {
/*  89:113 */     crearHerramienta();
/*  90:114 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:125 */       this.servicioHerramienta.eliminar(this.herramienta);
/*  98:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:129 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */     }
/* 105:131 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:141 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void crearHerramienta()
/* 114:    */   {
/* 115:148 */     this.herramienta = new Herramienta();
/* 116:149 */     this.herramienta.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 117:150 */     this.herramienta.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 118:151 */     this.herramienta.setActivo(true);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Herramienta getHerramienta()
/* 122:    */   {
/* 123:160 */     if (this.herramienta == null) {
/* 124:161 */       crearHerramienta();
/* 125:    */     }
/* 126:163 */     return this.herramienta;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setHerramienta(Herramienta Herramienta)
/* 130:    */   {
/* 131:173 */     this.herramienta = Herramienta;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public LazyDataModel<Herramienta> getListaHerramienta()
/* 135:    */   {
/* 136:182 */     return this.listaHerramienta;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setListaHerramienta(LazyDataModel<Herramienta> listaHerramienta)
/* 140:    */   {
/* 141:192 */     this.listaHerramienta = listaHerramienta;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public DataTable getDtHerramienta()
/* 145:    */   {
/* 146:201 */     return this.dtHerramienta;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDtHerramienta(DataTable dtHerramienta)
/* 150:    */   {
/* 151:211 */     this.dtHerramienta = dtHerramienta;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.HerramientaBean
 * JD-Core Version:    0.7.0.1
 */