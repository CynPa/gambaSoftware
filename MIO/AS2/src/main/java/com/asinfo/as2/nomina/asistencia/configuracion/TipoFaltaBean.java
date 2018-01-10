/*   1:    */ package com.asinfo.as2.nomina.asistencia.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Rubro;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.nomina.asistencia.TipoFalta;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
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
/*  27:    */ public class TipoFaltaBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioGenerico<TipoFalta> servicioTipoFalta;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioRubro servicioRubro;
/*  35:    */   private LazyDataModel<TipoFalta> listaTipoFalta;
/*  36:    */   private List<Rubro> listRubro;
/*  37:    */   private TipoFalta tipoFalta;
/*  38:    */   private DataTable dtTipoFalta;
/*  39:    */   
/*  40:    */   @PostConstruct
/*  41:    */   public void init()
/*  42:    */   {
/*  43: 58 */     this.listaTipoFalta = new LazyDataModel()
/*  44:    */     {
/*  45:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  46:    */       
/*  47:    */       public List<TipoFalta> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  48:    */       {
/*  49: 68 */         List<TipoFalta> lista = new ArrayList();
/*  50: 69 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  51: 70 */         filters = TipoFaltaBean.this.agregarFiltroOrganizacion(filters);
/*  52: 71 */         filters.put("idSucursal", String.valueOf(AppUtil.getSucursal().getIdSucursal()));
/*  53: 72 */         List<String> listaCampos = new ArrayList();
/*  54: 73 */         listaCampos.add("rubro");
/*  55: 74 */         lista = TipoFaltaBean.this.servicioTipoFalta.obtenerListaPorPagina(TipoFalta.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/*  56: 75 */         TipoFaltaBean.this.listaTipoFalta.setRowCount(TipoFaltaBean.this.servicioTipoFalta.contarPorCriterio(TipoFalta.class, filters));
/*  57:    */         
/*  58: 77 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65: 84 */     if ((getTipoFalta() != null) && (getTipoFalta().getId() != 0))
/*  66:    */     {
/*  67: 85 */       cargarDatos();
/*  68: 86 */       setEditado(true);
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72: 88 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  73:    */     }
/*  74: 90 */     return null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81: 96 */       if ((getTipoFalta().getRubro() == null) && (getTipoFalta().getNumeroDiasFalta() <= 0))
/*  82:    */       {
/*  83: 97 */         addErrorMessage(getLanguageController().getMensaje("msg_error_dias_falta_cero"));
/*  84:    */       }
/*  85:    */       else
/*  86:    */       {
/*  87: 99 */         if (getTipoFalta().getRubro() != null) {
/*  88:100 */           getTipoFalta().setNumeroDiasFalta(0);
/*  89:    */         }
/*  90:102 */         this.servicioTipoFalta.guardar(getTipoFalta());
/*  91:103 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:104 */         limpiar();
/*  93:105 */         setEditado(false);
/*  94:    */       }
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:108 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  99:109 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 100:    */     }
/* 101:111 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String eliminar()
/* 105:    */   {
/* 106:116 */     if ((getTipoFalta() != null) && (getTipoFalta().getId() != 0)) {
/* 107:    */       try
/* 108:    */       {
/* 109:118 */         this.servicioTipoFalta.eliminar(getTipoFalta());
/* 110:119 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 111:    */       }
/* 112:    */       catch (Exception e)
/* 113:    */       {
/* 114:121 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 115:122 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 116:    */       }
/* 117:    */     } else {
/* 118:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 119:    */     }
/* 120:127 */     return null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String limpiar()
/* 124:    */   {
/* 125:132 */     this.tipoFalta = new TipoFalta();
/* 126:133 */     this.tipoFalta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 127:134 */     this.tipoFalta.setIdSucursal(AppUtil.getSucursal().getId());
/* 128:135 */     this.tipoFalta.setActivo(true);
/* 129:136 */     return null;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cargarDatos()
/* 133:    */   {
/* 134:141 */     List<String> listaCampos = new ArrayList();
/* 135:142 */     listaCampos.add("rubro");
/* 136:143 */     setTipoFalta((TipoFalta)this.servicioTipoFalta.cargarDetalle(TipoFalta.class, this.tipoFalta.getId(), listaCampos));
/* 137:144 */     return null;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public LazyDataModel<TipoFalta> getListaTipoFalta()
/* 141:    */   {
/* 142:148 */     return this.listaTipoFalta;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setListaTipoFalta(LazyDataModel<TipoFalta> listaTipoFalta)
/* 146:    */   {
/* 147:152 */     this.listaTipoFalta = listaTipoFalta;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public TipoFalta getTipoFalta()
/* 151:    */   {
/* 152:156 */     return this.tipoFalta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setTipoFalta(TipoFalta tipoFalta)
/* 156:    */   {
/* 157:160 */     this.tipoFalta = tipoFalta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<Rubro> getListRubro()
/* 161:    */   {
/* 162:164 */     if (this.listRubro == null)
/* 163:    */     {
/* 164:165 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 165:166 */       filters.put("operacion", "-1");
/* 166:167 */       filters.put("tipoRubro", TipoRubro.VARIABLE.name());
/* 167:168 */       this.listRubro = this.servicioRubro.obtenerListaCombo("nombre", true, filters);
/* 168:    */     }
/* 169:170 */     return this.listRubro;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setListRubro(List<Rubro> listRubro)
/* 173:    */   {
/* 174:174 */     this.listRubro = listRubro;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public DataTable getDtTipoFalta()
/* 178:    */   {
/* 179:178 */     return this.dtTipoFalta;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDtTipoFalta(DataTable dtTipoFalta)
/* 183:    */   {
/* 184:182 */     this.dtTipoFalta = dtTipoFalta;
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.TipoFaltaBean
 * JD-Core Version:    0.7.0.1
 */