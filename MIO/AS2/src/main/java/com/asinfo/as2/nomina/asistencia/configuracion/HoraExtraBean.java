/*   1:    */ package com.asinfo.as2.nomina.asistencia.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Rubro;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
/*   9:    */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  12:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.component.datatable.DataTable;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class HoraExtraBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = -6642782572366933215L;
/*  34:    */   @EJB
/*  35:    */   private ServicioGenerico<HoraExtra> servicioHoraExtra;
/*  36:    */   @EJB
/*  37:    */   private ServicioAsistenciaConfiguracion servicioAsistenciaConfiguracion;
/*  38:    */   @EJB
/*  39:    */   private ServicioRubro servicioRubro;
/*  40:    */   private HoraExtra horaExtra;
/*  41:    */   private LazyDataModel<HoraExtra> listaHoraExtra;
/*  42:    */   private DataTable dtHoraExtra;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 85 */     this.listaHoraExtra = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  50:    */       
/*  51:    */       public List<HoraExtra> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 95 */         List<HoraExtra> lista = new ArrayList();
/*  54: 96 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  55:    */         
/*  56: 98 */         lista = HoraExtraBean.this.servicioAsistenciaConfiguracion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  57: 99 */         HoraExtraBean.this.listaHoraExtra.setRowCount(HoraExtraBean.this.servicioHoraExtra.contarPorCriterio(HoraExtra.class, filters));
/*  58:    */         
/*  59:101 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String crearHoraExtra()
/*  65:    */   {
/*  66:114 */     this.horaExtra = new HoraExtra();
/*  67:115 */     this.horaExtra.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  68:116 */     this.horaExtra.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  69:117 */     return "";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String limpiar()
/*  73:    */   {
/*  74:126 */     crearHoraExtra();
/*  75:127 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String editar()
/*  79:    */   {
/*  80:136 */     if ((getHoraExtra() != null) && (getHoraExtra().getId() != 0))
/*  81:    */     {
/*  82:137 */       this.horaExtra = this.servicioAsistenciaConfiguracion.cargarDetalleHoraExtra(this.horaExtra);
/*  83:138 */       setEditado(true);
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  88:    */     }
/*  89:142 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String guardar()
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:152 */       this.servicioHoraExtra.guardar(getHoraExtra());
/*  97:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  98:154 */       limpiar();
/*  99:155 */       setEditado(false);
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 104:158 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:    */     }
/* 106:160 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String eliminar()
/* 110:    */   {
/* 111:169 */     if ((getHoraExtra() != null) && (getHoraExtra().getId() != 0)) {
/* 112:    */       try
/* 113:    */       {
/* 114:171 */         this.servicioHoraExtra.eliminar(getHoraExtra());
/* 115:172 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 116:    */       }
/* 117:    */       catch (Exception e)
/* 118:    */       {
/* 119:174 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 120:175 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 121:    */       }
/* 122:    */     } else {
/* 123:178 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 124:    */     }
/* 125:180 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String cargarDatos()
/* 129:    */   {
/* 130:189 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public HoraExtra getHoraExtra()
/* 134:    */   {
/* 135:197 */     return this.horaExtra;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setHoraExtra(HoraExtra horaExtra)
/* 139:    */   {
/* 140:201 */     this.horaExtra = horaExtra;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public LazyDataModel<HoraExtra> getListaHoraExtra()
/* 144:    */   {
/* 145:205 */     return this.listaHoraExtra;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaHoraExtra(LazyDataModel<HoraExtra> listaHoraExtra)
/* 149:    */   {
/* 150:209 */     this.listaHoraExtra = listaHoraExtra;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public DataTable getDtHoraExtra()
/* 154:    */   {
/* 155:213 */     return this.dtHoraExtra;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setDtHoraExtra(DataTable dtHoraExtra)
/* 159:    */   {
/* 160:217 */     this.dtHoraExtra = dtHoraExtra;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<SelectItem> getListaPorCiento()
/* 164:    */   {
/* 165:221 */     List<SelectItem> lista = new ArrayList();
/* 166:222 */     for (PorCientoHoraExtra item : PorCientoHoraExtra.values()) {
/* 167:223 */       lista.add(new SelectItem(item, item.getNombre()));
/* 168:    */     }
/* 169:225 */     return lista;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<Rubro> getListaRubro()
/* 173:    */   {
/* 174:229 */     Map<String, String> filtros = new HashMap();
/* 175:230 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 176:231 */     filtros.put("tipo", TipoRubroEnum.HORAS_EXTRA.toString());
/* 177:232 */     return this.servicioRubro.obtenerListaCombo("nombre", true, filtros);
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.HoraExtraBean
 * JD-Core Version:    0.7.0.1
 */