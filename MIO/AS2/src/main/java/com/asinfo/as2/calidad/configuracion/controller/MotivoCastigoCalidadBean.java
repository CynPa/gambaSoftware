/*   1:    */ package com.asinfo.as2.calidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.calidad.MotivoCastigoCalidad;
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
/*  26:    */ public class MotivoCastigoCalidadBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<MotivoCastigoCalidad> servicioMotivoCastigoCalidad;
/*  32:    */   private MotivoCastigoCalidad motivoCastigoCalidad;
/*  33:    */   private LazyDataModel<MotivoCastigoCalidad> listaMotivoCastigoCalidad;
/*  34:    */   private DataTable dtMotivoCastigoCalidad;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 51 */     this.listaMotivoCastigoCalidad = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<MotivoCastigoCalidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 58 */         List<MotivoCastigoCalidad> lista = new ArrayList();
/*  46: 59 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 61 */         lista = MotivoCastigoCalidadBean.this.servicioMotivoCastigoCalidad.obtenerListaPorPagina(MotivoCastigoCalidad.class, startIndex, pageSize, sortField, ordenar, filters);
/*  49:    */         
/*  50: 63 */         MotivoCastigoCalidadBean.this.listaMotivoCastigoCalidad.setRowCount(MotivoCastigoCalidadBean.this.servicioMotivoCastigoCalidad.contarPorCriterio(MotivoCastigoCalidad.class, filters));
/*  51:    */         
/*  52: 65 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String editar()
/*  58:    */   {
/*  59: 78 */     if (getMotivoCastigoCalidad().getId() > 0) {
/*  60: 79 */       setEditado(true);
/*  61:    */     } else {
/*  62: 81 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  63:    */     }
/*  64: 83 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String guardar()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71: 94 */       this.servicioMotivoCastigoCalidad.guardarValidar(this.motivoCastigoCalidad);
/*  72: 95 */       limpiar();
/*  73: 96 */       setEditado(false);
/*  74: 97 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  75:    */     }
/*  76:    */     catch (AS2Exception e)
/*  77:    */     {
/*  78: 99 */       JsfUtil.addErrorMessage(e, "");
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:101 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:102 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:104 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String limpiar()
/*  89:    */   {
/*  90:114 */     crearMotivoCastigoCalidad();
/*  91:115 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String eliminar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:126 */       this.servicioMotivoCastigoCalidad.eliminar(this.motivoCastigoCalidad);
/*  99:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 104:130 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 105:    */     }
/* 106:132 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String cargarDatos()
/* 110:    */   {
/* 111:142 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void crearMotivoCastigoCalidad()
/* 115:    */   {
/* 116:149 */     this.motivoCastigoCalidad = new MotivoCastigoCalidad();
/* 117:150 */     this.motivoCastigoCalidad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 118:151 */     this.motivoCastigoCalidad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 119:152 */     this.motivoCastigoCalidad.setActivo(true);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public MotivoCastigoCalidad getMotivoCastigoCalidad()
/* 123:    */   {
/* 124:161 */     if (this.motivoCastigoCalidad == null) {
/* 125:162 */       crearMotivoCastigoCalidad();
/* 126:    */     }
/* 127:164 */     return this.motivoCastigoCalidad;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setMotivoCastigoCalidad(MotivoCastigoCalidad MotivoCastigoCalidad)
/* 131:    */   {
/* 132:174 */     this.motivoCastigoCalidad = MotivoCastigoCalidad;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public LazyDataModel<MotivoCastigoCalidad> getListaMotivoCastigoCalidad()
/* 136:    */   {
/* 137:183 */     return this.listaMotivoCastigoCalidad;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setListaMotivoCastigoCalidad(LazyDataModel<MotivoCastigoCalidad> listaMotivoCastigoCalidad)
/* 141:    */   {
/* 142:193 */     this.listaMotivoCastigoCalidad = listaMotivoCastigoCalidad;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public DataTable getDtMotivoCastigoCalidad()
/* 146:    */   {
/* 147:202 */     return this.dtMotivoCastigoCalidad;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setDtMotivoCastigoCalidad(DataTable dtMotivoCastigoCalidad)
/* 151:    */   {
/* 152:212 */     this.dtMotivoCastigoCalidad = dtMotivoCastigoCalidad;
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.configuracion.controller.MotivoCastigoCalidadBean
 * JD-Core Version:    0.7.0.1
 */