/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.InstitucionEducativa;
/*   6:    */ import com.asinfo.as2.entities.NivelInstruccion;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioInstitucionEducativa;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioNivelInstruccion;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class InstitucionEducativaBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -8733313743588124540L;
/*  29:    */   @EJB
/*  30:    */   private transient ServicioNivelInstruccion servicioNivelInstruccion;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioInstitucionEducativa servicioInstitucionEducativa;
/*  33:    */   private InstitucionEducativa institucionEducativa;
/*  34:    */   private NivelInstruccion nivelInstruccion;
/*  35:    */   private List<NivelInstruccion> listaNivelInstruccion;
/*  36:    */   private LazyDataModel<InstitucionEducativa> listaInstitucionEducativa;
/*  37:    */   private DataTable dtInstitucioEducativa;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 74 */     this.listaInstitucionEducativa = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 6332796485708443987L;
/*  45:    */       
/*  46:    */       public List<InstitucionEducativa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 80 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  49: 81 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  50: 82 */         List<InstitucionEducativa> lista = InstitucionEducativaBean.this.servicioInstitucionEducativa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */         
/*  52:    */ 
/*  53: 85 */         InstitucionEducativaBean.this.listaInstitucionEducativa.setRowCount(InstitucionEducativaBean.this.servicioInstitucionEducativa.contarPorCriterio(filters));
/*  54: 86 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void crearEntidad()
/*  60:    */   {
/*  61: 99 */     this.institucionEducativa = new InstitucionEducativa();
/*  62:100 */     this.institucionEducativa.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  63:101 */     this.institucionEducativa.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String editar()
/*  67:    */   {
/*  68:110 */     if ((getInstitucionEducativa() != null) && (getInstitucionEducativa().getIdInstitucionEducativa() != 0)) {
/*  69:111 */       setEditado(true);
/*  70:    */     } else {
/*  71:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  72:    */     }
/*  73:115 */     return "";
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String guardar()
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80:125 */       this.servicioInstitucionEducativa.guardar(this.institucionEducativa);
/*  81:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  82:127 */       setEditado(false);
/*  83:128 */       limpiar();
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  88:131 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  89:    */     }
/*  90:133 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:143 */       this.servicioInstitucionEducativa.eliminar(this.institucionEducativa);
/*  98:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:147 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */     }
/* 105:149 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:158 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String limpiar()
/* 114:    */   {
/* 115:167 */     crearEntidad();
/* 116:168 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public InstitucionEducativa getInstitucionEducativa()
/* 120:    */   {
/* 121:181 */     if (this.institucionEducativa == null) {
/* 122:182 */       this.institucionEducativa = new InstitucionEducativa();
/* 123:    */     }
/* 124:184 */     return this.institucionEducativa;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setInstitucionEducativa(InstitucionEducativa institucionEducativa)
/* 128:    */   {
/* 129:194 */     this.institucionEducativa = institucionEducativa;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public NivelInstruccion getNivelInstruccion()
/* 133:    */   {
/* 134:203 */     return this.nivelInstruccion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setNivelInstruccion(NivelInstruccion nivelInstruccion)
/* 138:    */   {
/* 139:213 */     this.nivelInstruccion = nivelInstruccion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public LazyDataModel<InstitucionEducativa> getListaInstitucionEducativa()
/* 143:    */   {
/* 144:222 */     return this.listaInstitucionEducativa;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setListaInstitucionEducativa(LazyDataModel<InstitucionEducativa> listaInstitucionEducativa)
/* 148:    */   {
/* 149:232 */     this.listaInstitucionEducativa = listaInstitucionEducativa;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public DataTable getDtInstitucioEducativa()
/* 153:    */   {
/* 154:241 */     return this.dtInstitucioEducativa;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setDtInstitucioEducativa(DataTable dtInstitucioEducativa)
/* 158:    */   {
/* 159:251 */     this.dtInstitucioEducativa = dtInstitucioEducativa;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<NivelInstruccion> getListaNivelInstruccion()
/* 163:    */   {
/* 164:255 */     if (this.listaNivelInstruccion == null) {
/* 165:256 */       this.listaNivelInstruccion = this.servicioNivelInstruccion.obtenerListaCombo(null, false, null);
/* 166:    */     }
/* 167:258 */     return this.listaNivelInstruccion;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.InstitucionEducativaBean
 * JD-Core Version:    0.7.0.1
 */