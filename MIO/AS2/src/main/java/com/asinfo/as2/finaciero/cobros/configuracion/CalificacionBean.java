/*   1:    */ package com.asinfo.as2.finaciero.cobros.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Calificacion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class CalificacionBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -9061218854698018535L;
/*  31:    */   @EJB
/*  32:    */   private ServicioGenerico<Calificacion> servicioCalificacion;
/*  33:    */   private Calificacion calificacion;
/*  34:    */   private LazyDataModel<Calificacion> listaCalificacion;
/*  35:    */   private DataTable dtCalificacion;
/*  36:    */   
/*  37:    */   @PostConstruct
/*  38:    */   public void init()
/*  39:    */   {
/*  40: 42 */     this.listaCalificacion = new LazyDataModel()
/*  41:    */     {
/*  42:    */       private static final long serialVersionUID = 1L;
/*  43:    */       
/*  44:    */       public List<Calificacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  45:    */       {
/*  46: 47 */         List<Calificacion> lista = new ArrayList();
/*  47: 48 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48: 49 */         lista = CalificacionBean.this.servicioCalificacion.obtenerListaPorPagina(Calificacion.class, startIndex, pageSize, sortField, ordenar, filters);
/*  49: 50 */         CalificacionBean.this.listaCalificacion.setRowCount(CalificacionBean.this.servicioCalificacion.contarPorCriterio(Calificacion.class, filters));
/*  50: 51 */         return lista;
/*  51:    */       }
/*  52:    */     };
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String editar()
/*  56:    */   {
/*  57: 57 */     if ((this.calificacion != null) && (this.calificacion.getIdCalificacion() != 0))
/*  58:    */     {
/*  59: 58 */       this.calificacion = ((Calificacion)this.servicioCalificacion.cargarDetalle(Calificacion.class, this.calificacion.getIdCalificacion(), null));
/*  60: 59 */       setEditado(true);
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 61 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  65:    */     }
/*  66: 63 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String guardar()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 68 */       validarCalificacion();
/*  74: 69 */       this.servicioCalificacion.guardar(this.calificacion);
/*  75: 70 */       limpiar();
/*  76: 71 */       setEditado(false);
/*  77: 72 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:    */     }
/*  79:    */     catch (AS2Exception e)
/*  80:    */     {
/*  81: 74 */       JsfUtil.addErrorMessage(e, "");
/*  82: 75 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86: 77 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87: 78 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89: 80 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94: 84 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String cargarDatos()
/*  98:    */   {
/*  99: 88 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String limpiar()
/* 103:    */   {
/* 104: 92 */     crearCalificacion();
/* 105: 93 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void crearCalificacion()
/* 109:    */   {
/* 110: 97 */     this.calificacion = new Calificacion();
/* 111: 98 */     this.calificacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 112: 99 */     this.calificacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 113:100 */     this.calificacion.setActivo(true);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean validarCalificacion()
/* 117:    */     throws AS2Exception
/* 118:    */   {
/* 119:104 */     if (this.calificacion.getRangoDesde() >= this.calificacion.getRangoHasta()) {
/* 120:105 */       throw new AS2Exception("com.asinfo.as2.RANGO_CALIFICACION_DESDE_MAYOR_HASTA", new String[] { "" });
/* 121:    */     }
/* 122:107 */     HashMap<String, String> filters = new HashMap();
/* 123:108 */     filters.put("idOrganizacion", "=" + AppUtil.getOrganizacion().getId());
/* 124:109 */     filters.put("idCalificacion", "!=" + this.calificacion.getIdCalificacion());
/* 125:110 */     List<Calificacion> lista = this.servicioCalificacion.obtenerListaCombo(Calificacion.class, "", true, filters);
/* 126:111 */     for (Calificacion c : lista)
/* 127:    */     {
/* 128:112 */       if ((c.getRangoDesde() <= this.calificacion.getRangoDesde()) && (this.calificacion.getRangoDesde() <= c.getRangoHasta())) {
/* 129:113 */         throw new AS2Exception("com.asinfo.as2.ERROR_RANGO_CALIFICACION_DUPLICADO", new String[] { "" });
/* 130:    */       }
/* 131:115 */       if ((c.getRangoDesde() <= this.calificacion.getRangoHasta()) && (this.calificacion.getRangoHasta() <= c.getRangoHasta())) {
/* 132:116 */         throw new AS2Exception("com.asinfo.as2.RANGO_CALIFICACION_DESDE_MAYOR_HASTA", new String[] { "" });
/* 133:    */       }
/* 134:    */     }
/* 135:119 */     return true;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Calificacion getCalificacion()
/* 139:    */   {
/* 140:123 */     return this.calificacion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setCalificacion(Calificacion calificacion)
/* 144:    */   {
/* 145:127 */     this.calificacion = calificacion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public LazyDataModel<Calificacion> getListaCalificacion()
/* 149:    */   {
/* 150:131 */     return this.listaCalificacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaCalificacion(LazyDataModel<Calificacion> listaCalificacion)
/* 154:    */   {
/* 155:135 */     this.listaCalificacion = listaCalificacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public DataTable getDtCalificacion()
/* 159:    */   {
/* 160:139 */     return this.dtCalificacion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setDtCalificacion(DataTable dtCalificacion)
/* 164:    */   {
/* 165:143 */     this.dtCalificacion = dtCalificacion;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.configuracion.CalificacionBean
 * JD-Core Version:    0.7.0.1
 */