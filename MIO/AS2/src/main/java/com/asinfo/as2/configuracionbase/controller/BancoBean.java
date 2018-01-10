/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Banco;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
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
/*  19:    */ import javax.faces.model.SelectItem;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class BancoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<Banco> servicioBanco;
/*  34:    */   private Banco banco;
/*  35:    */   private LazyDataModel<Banco> listaBanco;
/*  36:    */   private List<SelectItem> bancoItems;
/*  37:    */   private DataTable dtBanco;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 60 */     this.listaBanco = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<Banco> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 67 */         List<Banco> lista = new ArrayList();
/*  49: 68 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 70 */         lista = BancoBean.this.servicioBanco.obtenerListaPorPagina(Banco.class, startIndex, pageSize, sortField, ordenar, filters);
/*  52: 71 */         BancoBean.this.listaBanco.setRowCount(BancoBean.this.servicioBanco.contarPorCriterio(Banco.class, filters));
/*  53:    */         
/*  54: 73 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String editar()
/*  60:    */   {
/*  61: 86 */     if (getBanco().getId() > 0) {
/*  62: 87 */       setEditado(true);
/*  63:    */     } else {
/*  64: 89 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  65:    */     }
/*  66: 91 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String guardar()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:102 */       this.servicioBanco.guardarValidar(this.banco);
/*  74:103 */       limpiar();
/*  75:104 */       setEditado(false);
/*  76:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  77:    */     }
/*  78:    */     catch (AS2Exception e)
/*  79:    */     {
/*  80:107 */       JsfUtil.addErrorMessage(e, "");
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:110 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:112 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String limpiar()
/*  91:    */   {
/*  92:122 */     crearBanco();
/*  93:123 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:134 */       this.servicioBanco.eliminar(this.banco);
/* 101:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:137 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 106:138 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 107:    */     }
/* 108:140 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:150 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void cargarDatosBanco()
/* 117:    */   {
/* 118:171 */     List<Banco> bancos = new ArrayList();
/* 119:172 */     bancos = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/* 120:173 */     this.bancoItems = new ArrayList();
/* 121:175 */     for (Banco bancoX : bancos)
/* 122:    */     {
/* 123:176 */       int value = bancoX.getIdBanco();
/* 124:177 */       String label = bancoX.getNombre();
/* 125:178 */       SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 126:179 */       this.bancoItems.add(opcion);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void crearBanco()
/* 131:    */   {
/* 132:189 */     this.banco = new Banco();
/* 133:190 */     this.banco.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 134:191 */     this.banco.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void onRowSelect(SelectEvent event)
/* 138:    */   {
/* 139:198 */     Banco banco1 = (Banco)event.getObject();
/* 140:199 */     setBanco(banco1);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Banco getBanco()
/* 144:    */   {
/* 145:208 */     if (this.banco == null) {
/* 146:209 */       crearBanco();
/* 147:    */     }
/* 148:211 */     return this.banco;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setBanco(Banco banco)
/* 152:    */   {
/* 153:221 */     this.banco = banco;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public LazyDataModel<Banco> getListaBanco()
/* 157:    */   {
/* 158:230 */     return this.listaBanco;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setListaBanco(LazyDataModel<Banco> listaBanco)
/* 162:    */   {
/* 163:240 */     this.listaBanco = listaBanco;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public List<SelectItem> getBancoItems()
/* 167:    */   {
/* 168:249 */     return this.bancoItems;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setBancoItems(List<SelectItem> bancoItems)
/* 172:    */   {
/* 173:259 */     this.bancoItems = bancoItems;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public DataTable getDtBanco()
/* 177:    */   {
/* 178:268 */     return this.dtBanco;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDtBanco(DataTable dtBanco)
/* 182:    */   {
/* 183:278 */     this.dtBanco = dtBanco;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.BancoBean
 * JD-Core Version:    0.7.0.1
 */