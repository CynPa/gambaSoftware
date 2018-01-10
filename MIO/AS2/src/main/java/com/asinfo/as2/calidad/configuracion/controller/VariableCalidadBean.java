/*   1:    */ package com.asinfo.as2.calidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.calidad.CategoriaVariableCalidad;
/*   8:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.JsfUtil;
/*  13:    */ import java.util.ArrayList;
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
/*  27:    */ public class VariableCalidadBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioGenerico<VariableCalidad> servicioVariableCalidad;
/*  33:    */   @EJB
/*  34:    */   private ServicioGenerico<CategoriaVariableCalidad> servicioCategoriaVariableCalidad;
/*  35:    */   private VariableCalidad variableCalidad;
/*  36:    */   private LazyDataModel<VariableCalidad> listaVariableCalidad;
/*  37:    */   private DataTable dtVariableCalidad;
/*  38:    */   private List<CategoriaVariableCalidad> listaCategoriaVariableCalidad;
/*  39:    */   
/*  40:    */   @PostConstruct
/*  41:    */   public void init()
/*  42:    */   {
/*  43: 55 */     this.listaVariableCalidad = new LazyDataModel()
/*  44:    */     {
/*  45:    */       private static final long serialVersionUID = 1L;
/*  46:    */       
/*  47:    */       public List<VariableCalidad> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  48:    */       {
/*  49: 62 */         List<VariableCalidad> lista = new ArrayList();
/*  50: 63 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  51:    */         
/*  52: 65 */         List<String> lcampos = new ArrayList();
/*  53: 66 */         lcampos.add("categoriaVariableCalidad");
/*  54: 67 */         lista = VariableCalidadBean.this.servicioVariableCalidad.obtenerListaPorPagina(VariableCalidad.class, startIndex, pageSize, sortField, ordenar, filters, lcampos);
/*  55:    */         
/*  56: 69 */         VariableCalidadBean.this.listaVariableCalidad.setRowCount(VariableCalidadBean.this.servicioVariableCalidad.contarPorCriterio(VariableCalidad.class, filters));
/*  57:    */         
/*  58: 71 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65: 84 */     if (getVariableCalidad().getId() > 0) {
/*  66: 85 */       setEditado(true);
/*  67:    */     } else {
/*  68: 87 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70: 89 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:100 */       if ((this.variableCalidad.isIndicadorMateriaPrima()) || (this.variableCalidad.isIndicadorProductoTerminado()))
/*  78:    */       {
/*  79:101 */         this.servicioVariableCalidad.guardarValidar(this.variableCalidad);
/*  80:102 */         limpiar();
/*  81:103 */         setEditado(false);
/*  82:104 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86:106 */         addErrorMessage(getLanguageController().getMensaje("msg_error_variable_indicador_mp_pt"));
/*  87:    */       }
/*  88:    */     }
/*  89:    */     catch (AS2Exception e)
/*  90:    */     {
/*  91:109 */       JsfUtil.addErrorMessage(e, "");
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  96:112 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  97:    */     }
/*  98:114 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String limpiar()
/* 102:    */   {
/* 103:124 */     crearVariableCalidad();
/* 104:125 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String eliminar()
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:136 */       this.servicioVariableCalidad.eliminar(this.variableCalidad);
/* 112:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 117:140 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 118:    */     }
/* 119:142 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String cargarDatos()
/* 123:    */   {
/* 124:152 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void crearVariableCalidad()
/* 128:    */   {
/* 129:159 */     this.variableCalidad = new VariableCalidad();
/* 130:160 */     this.variableCalidad.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 131:161 */     this.variableCalidad.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 132:162 */     this.variableCalidad.setActivo(true);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public VariableCalidad getVariableCalidad()
/* 136:    */   {
/* 137:171 */     if (this.variableCalidad == null) {
/* 138:172 */       crearVariableCalidad();
/* 139:    */     }
/* 140:174 */     return this.variableCalidad;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setVariableCalidad(VariableCalidad VariableCalidad)
/* 144:    */   {
/* 145:184 */     this.variableCalidad = VariableCalidad;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public LazyDataModel<VariableCalidad> getListaVariableCalidad()
/* 149:    */   {
/* 150:193 */     return this.listaVariableCalidad;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaVariableCalidad(LazyDataModel<VariableCalidad> listaVariableCalidad)
/* 154:    */   {
/* 155:203 */     this.listaVariableCalidad = listaVariableCalidad;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public DataTable getDtVariableCalidad()
/* 159:    */   {
/* 160:212 */     return this.dtVariableCalidad;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setDtVariableCalidad(DataTable dtVariableCalidad)
/* 164:    */   {
/* 165:222 */     this.dtVariableCalidad = dtVariableCalidad;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<CategoriaVariableCalidad> getListaCategoriaVariableCalidad()
/* 169:    */   {
/* 170:229 */     if (this.listaCategoriaVariableCalidad == null) {
/* 171:230 */       this.listaCategoriaVariableCalidad = this.servicioCategoriaVariableCalidad.obtenerListaCombo(CategoriaVariableCalidad.class, "nombre", true, null);
/* 172:    */     }
/* 173:233 */     return this.listaCategoriaVariableCalidad;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setListaCategoriaVariableCalidad(List<CategoriaVariableCalidad> listaCategoriaVariableCalidad)
/* 177:    */   {
/* 178:241 */     this.listaCategoriaVariableCalidad = listaCategoriaVariableCalidad;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.configuracion.controller.VariableCalidadBean
 * JD-Core Version:    0.7.0.1
 */