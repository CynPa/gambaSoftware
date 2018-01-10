/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Especialidad;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ import org.primefaces.model.LazyDataModel;
/*  19:    */ import org.primefaces.model.SortOrder;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class EspecialidadBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -5210548908963639617L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioEspecialidad servicioEspecialidad;
/*  29:    */   private Especialidad especialidad;
/*  30:    */   private LazyDataModel<Especialidad> listaEspecialidad;
/*  31:    */   private DataTable dtEspecialidad;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 69 */     this.listaEspecialidad = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = -1205793702572475302L;
/*  39:    */       
/*  40:    */       public List<Especialidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43:    */         
/*  44: 83 */         List<Especialidad> lista = EspecialidadBean.this.servicioEspecialidad.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  45:    */         
/*  46: 85 */         EspecialidadBean.this.listaEspecialidad.setRowCount(EspecialidadBean.this.servicioEspecialidad.contarPorCriterio(filters));
/*  47: 86 */         return lista;
/*  48:    */       }
/*  49:    */     };
/*  50:    */   }
/*  51:    */   
/*  52:    */   private void crearEntidad()
/*  53:    */   {
/*  54:100 */     this.especialidad = new Especialidad();
/*  55:101 */     this.especialidad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  56:102 */     this.especialidad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  57:103 */     this.especialidad.setActivo(true);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String editar()
/*  61:    */   {
/*  62:112 */     if (getEspecialidad().getIdEspecialidad() > 0) {
/*  63:113 */       setEditado(true);
/*  64:    */     } else {
/*  65:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  66:    */     }
/*  67:117 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:127 */       LOG.info("Id Especialidad >>>>>>" + this.especialidad.getIdEspecialidad());
/*  75:128 */       this.servicioEspecialidad.guardar(this.especialidad);
/*  76:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:130 */       setEditado(false);
/*  78:131 */       limpiar();
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  83:134 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  84:    */     }
/*  85:136 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String eliminar()
/*  89:    */   {
/*  90:145 */     if (getEspecialidad().getIdEspecialidad() > 0) {
/*  91:    */       try
/*  92:    */       {
/*  93:147 */         setEspecialidad(this.servicioEspecialidad.cargarDetalle(getEspecialidad().getIdEspecialidad()));
/*  94:    */         
/*  95:    */ 
/*  96:    */ 
/*  97:151 */         this.especialidad.setEliminado(true);
/*  98:152 */         this.servicioEspecialidad.eliminar(this.especialidad);
/*  99:153 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 100:    */       }
/* 101:    */       catch (Exception e)
/* 102:    */       {
/* 103:155 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 104:156 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 105:    */       }
/* 106:    */     } else {
/* 107:159 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 108:    */     }
/* 109:161 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String cargarDatos()
/* 113:    */   {
/* 114:170 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String limpiar()
/* 118:    */   {
/* 119:179 */     crearEntidad();
/* 120:180 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Especialidad getEspecialidad()
/* 124:    */   {
/* 125:196 */     if (this.especialidad == null) {
/* 126:197 */       this.especialidad = new Especialidad();
/* 127:    */     }
/* 128:199 */     return this.especialidad;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setEspecialidad(Especialidad especialidad)
/* 132:    */   {
/* 133:209 */     this.especialidad = especialidad;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public LazyDataModel<Especialidad> getListaEspecialidad()
/* 137:    */   {
/* 138:218 */     return this.listaEspecialidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setListaEspecialidad(LazyDataModel<Especialidad> listaEspecialidad)
/* 142:    */   {
/* 143:228 */     this.listaEspecialidad = listaEspecialidad;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DataTable getDtEspecialidad()
/* 147:    */   {
/* 148:237 */     return this.dtEspecialidad;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDtEspecialidad(DataTable dtEspecialidad)
/* 152:    */   {
/* 153:247 */     this.dtEspecialidad = dtEspecialidad;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.EspecialidadBean
 * JD-Core Version:    0.7.0.1
 */