/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Rubro;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoSubsidio;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoSubsidio;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import java.io.Serializable;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class TipoSubsidioBean
/*  29:    */   extends PageControllerAS2
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -6442694289771440692L;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioTipoSubsidio servicioTipoSubsidio;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioRubro servicioRubro;
/*  37:    */   private TipoSubsidio tipoSubsidio;
/*  38:    */   private transient List<Rubro> listaRubro;
/*  39:    */   private String rubroSelecionado;
/*  40:    */   private LazyDataModel<TipoSubsidio> listaTipoSubsidio;
/*  41:    */   private DataTable dtTipoSubsidio;
/*  42:    */   
/*  43:    */   @PostConstruct
/*  44:    */   public void init()
/*  45:    */   {
/*  46: 82 */     this.listaTipoSubsidio = new LazyDataModel()
/*  47:    */     {
/*  48:    */       public List<TipoSubsidio> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  49:    */       {
/*  50: 88 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  51:    */         
/*  52: 90 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  53: 91 */         List<TipoSubsidio> lista = TipoSubsidioBean.this.servicioTipoSubsidio.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  54:    */         
/*  55: 93 */         TipoSubsidioBean.this.listaTipoSubsidio.setRowCount(TipoSubsidioBean.this.servicioTipoSubsidio.contarPorCriterio(filters));
/*  56:    */         
/*  57: 95 */         return lista;
/*  58:    */       }
/*  59:    */     };
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void crearEntidad()
/*  63:    */   {
/*  64:109 */     this.tipoSubsidio = new TipoSubsidio();
/*  65:110 */     this.tipoSubsidio.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  66:111 */     this.tipoSubsidio.setIdSucursal(AppUtil.getSucursal().getId());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String editar()
/*  70:    */   {
/*  71:120 */     if ((getTipoSubsidio() != null) && (getTipoSubsidio().getIdTipoSubsidio() != 0))
/*  72:    */     {
/*  73:121 */       cargarDatos();
/*  74:122 */       setEditado(true);
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:124 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  79:    */     }
/*  80:126 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String guardar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:136 */       this.servicioTipoSubsidio.guardar(this.tipoSubsidio);
/*  88:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  89:138 */       setEditado(false);
/*  90:139 */       limpiar();
/*  91:    */     }
/*  92:    */     catch (AS2Exception e)
/*  93:    */     {
/*  94:141 */       JsfUtil.addErrorMessage(e, "");
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  99:144 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 100:    */     }
/* 101:146 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String eliminar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:156 */       this.servicioTipoSubsidio.eliminar(this.tipoSubsidio);
/* 109:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 110:    */     }
/* 111:    */     catch (Exception e)
/* 112:    */     {
/* 113:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 114:160 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 115:    */     }
/* 116:162 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String cargarDatos()
/* 120:    */   {
/* 121:171 */     TipoSubsidio tipoSubsidio = this.servicioTipoSubsidio.cargarDetalle(getTipoSubsidio().getIdTipoSubsidio());
/* 122:172 */     setTipoSubsidio(tipoSubsidio);
/* 123:173 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String limpiar()
/* 127:    */   {
/* 128:182 */     crearEntidad();
/* 129:183 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public TipoSubsidio getTipoSubsidio()
/* 133:    */   {
/* 134:200 */     if (this.tipoSubsidio == null) {
/* 135:201 */       this.tipoSubsidio = new TipoSubsidio();
/* 136:    */     }
/* 137:203 */     return this.tipoSubsidio;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setTipoSubsidio(TipoSubsidio tipoSubsidio)
/* 141:    */   {
/* 142:213 */     this.tipoSubsidio = tipoSubsidio;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public LazyDataModel<TipoSubsidio> getListaTipoSubsidio()
/* 146:    */   {
/* 147:222 */     return this.listaTipoSubsidio;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setListaTipoSubsidio(LazyDataModel<TipoSubsidio> listaTipoSubsidio)
/* 151:    */   {
/* 152:232 */     this.listaTipoSubsidio = listaTipoSubsidio;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public DataTable getDtTipoSubsidio()
/* 156:    */   {
/* 157:241 */     return this.dtTipoSubsidio;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDtTipoSubsidio(DataTable dtTipoSubsidio)
/* 161:    */   {
/* 162:251 */     this.dtTipoSubsidio = dtTipoSubsidio;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<Rubro> getListaRubro()
/* 166:    */   {
/* 167:260 */     if (this.listaRubro == null) {
/* 168:261 */       this.listaRubro = this.servicioRubro.obtenerListaCombo("nombre", true, null);
/* 169:    */     }
/* 170:263 */     return this.listaRubro;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String getRubroSelecionado()
/* 174:    */   {
/* 175:272 */     this.rubroSelecionado = "";
/* 176:273 */     if (getTipoSubsidio().getRubro() != null) {
/* 177:274 */       this.rubroSelecionado = Integer.toString(getTipoSubsidio().getRubro().getId());
/* 178:    */     }
/* 179:277 */     return this.rubroSelecionado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setRubroSelecionado(String rubroSelecionado)
/* 183:    */   {
/* 184:288 */     if (this.rubroSelecionado != rubroSelecionado)
/* 185:    */     {
/* 186:289 */       this.rubroSelecionado = rubroSelecionado;
/* 187:291 */       if (!this.rubroSelecionado.equals(""))
/* 188:    */       {
/* 189:292 */         int idRubroSelecionado = Integer.parseInt(this.rubroSelecionado);
/* 190:293 */         Rubro auxRubroSelecionado = this.servicioRubro.buscarPorId(idRubroSelecionado);
/* 191:294 */         getTipoSubsidio().setRubro(auxRubroSelecionado);
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.TipoSubsidioBean
 * JD-Core Version:    0.7.0.1
 */