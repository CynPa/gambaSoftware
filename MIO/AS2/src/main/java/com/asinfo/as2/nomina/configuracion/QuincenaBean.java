/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Quincena;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.enumeraciones.DiaMes;
/*   9:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class QuincenaBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioQuincena servicioQuincena;
/*  33:    */   private Quincena quincena;
/*  34:    */   private List<SelectItem> listaMes;
/*  35:    */   private List<SelectItem> listaDiaMes;
/*  36:    */   private LazyDataModel<Quincena> listaQuincena;
/*  37:    */   private DataTable dtQuincena;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 77 */     this.listaQuincena = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<Quincena> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 84 */         List<Quincena> lista = new ArrayList();
/*  49: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 87 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  52: 88 */         lista = QuincenaBean.this.servicioQuincena.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  53:    */         
/*  54: 90 */         QuincenaBean.this.listaQuincena.setRowCount(QuincenaBean.this.servicioQuincena.contarPorCriterio(filters));
/*  55:    */         
/*  56: 92 */         return lista;
/*  57:    */       }
/*  58:    */     };
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void crearQuincena()
/*  62:    */   {
/*  63:106 */     this.quincena = new Quincena();
/*  64:107 */     this.quincena.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  65:108 */     this.quincena.setIdSucursal(AppUtil.getSucursal().getId());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70:118 */     if ((getQuincena() != null) && (getQuincena().getIdQuincena() != 0)) {
/*  71:119 */       setEditado(true);
/*  72:    */     } else {
/*  73:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  74:    */     }
/*  75:123 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String guardar()
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:133 */       this.servicioQuincena.guardar(this.quincena);
/*  83:134 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  84:135 */       setEditado(false);
/*  85:136 */       limpiar();
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:138 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  90:139 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  91:    */     }
/*  92:141 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String eliminar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:151 */       this.servicioQuincena.eliminar(this.quincena);
/* 100:152 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:155 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */     }
/* 107:157 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarDatos()
/* 111:    */   {
/* 112:166 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String limpiar()
/* 116:    */   {
/* 117:175 */     crearQuincena();
/* 118:176 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<SelectItem> getListaDiaMes()
/* 122:    */   {
/* 123:185 */     if (this.listaDiaMes == null)
/* 124:    */     {
/* 125:186 */       this.listaDiaMes = new ArrayList();
/* 126:187 */       for (DiaMes diaMes : DiaMes.values())
/* 127:    */       {
/* 128:188 */         SelectItem item = new SelectItem(diaMes, diaMes.getNombre());
/* 129:189 */         this.listaDiaMes.add(item);
/* 130:    */       }
/* 131:    */     }
/* 132:192 */     return this.listaDiaMes;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List<SelectItem> getListaMes()
/* 136:    */   {
/* 137:199 */     if (this.listaMes == null)
/* 138:    */     {
/* 139:200 */       this.listaMes = new ArrayList();
/* 140:201 */       SelectItem item = new SelectItem(Integer.valueOf(0), "TODOS");
/* 141:202 */       this.listaMes.add(item);
/* 142:203 */       for (Mes t : Mes.values())
/* 143:    */       {
/* 144:204 */         item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 145:205 */         this.listaMes.add(item);
/* 146:    */       }
/* 147:    */     }
/* 148:208 */     return this.listaMes;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Quincena getQuincena()
/* 152:    */   {
/* 153:220 */     if (this.quincena == null) {
/* 154:221 */       this.quincena = new Quincena();
/* 155:    */     }
/* 156:223 */     return this.quincena;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setQuincena(Quincena quincena)
/* 160:    */   {
/* 161:233 */     this.quincena = quincena;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public LazyDataModel<Quincena> getListaQuincena()
/* 165:    */   {
/* 166:242 */     return this.listaQuincena;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setListaQuincena(LazyDataModel<Quincena> listaQuincena)
/* 170:    */   {
/* 171:252 */     this.listaQuincena = listaQuincena;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public DataTable getDtQuincena()
/* 175:    */   {
/* 176:261 */     return this.dtQuincena;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setDtQuincena(DataTable dtQuincena)
/* 180:    */   {
/* 181:271 */     this.dtQuincena = dtQuincena;
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.QuincenaBean
 * JD-Core Version:    0.7.0.1
 */