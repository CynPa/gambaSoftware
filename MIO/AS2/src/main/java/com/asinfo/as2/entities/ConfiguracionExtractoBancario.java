/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.OrderBy;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="configuracion_extracto_bancario")
/*  24:    */ public class ConfiguracionExtractoBancario
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="configuracion_extracto_bancario", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="configuracion_extracto_bancario")
/*  32:    */   @Column(name="id_configuracion_extracto_bancario")
/*  33:    */   private int idConfiguracionExtractoBancario;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @NotNull
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=false)
/*  41:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  42:    */   @Min(1L)
/*  43:    */   @Column(name="columna_operacion")
/*  44:    */   private Integer columnaOperacion;
/*  45:    */   @Min(1L)
/*  46:    */   @Column(name="columna_monto")
/*  47:    */   private Integer columnaMonto;
/*  48:    */   @Min(1L)
/*  49:    */   @Column(name="columna_fecha")
/*  50:    */   private Integer columnaFecha;
/*  51:    */   @NotNull
/*  52:    */   @Size(min=6, max=20)
/*  53:    */   @Column(name="formato_fecha")
/*  54:    */   private String formatoFecha;
/*  55:    */   @Column(name="descripcion", length=200)
/*  56:    */   @Size(max=200)
/*  57:    */   private String descripcion;
/*  58:    */   @Column(name="activo", nullable=false)
/*  59:    */   private boolean activo;
/*  60:    */   @Column(name="predeterminado", nullable=false)
/*  61:    */   private boolean predeterminado;
/*  62:    */   @OrderBy("id_configuracion_extracto_bancario")
/*  63:    */   @OneToMany(mappedBy="configuracionExtractoBancario", fetch=FetchType.LAZY)
/*  64: 89 */   List<DetalleConfiguracionExtractoBancario> listaDetalleConfiguracionExtractoBancario = new ArrayList();
/*  65:    */   
/*  66:    */   public int getId()
/*  67:    */   {
/*  68: 98 */     return this.idConfiguracionExtractoBancario;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdConfiguracionExtractoBancario()
/*  72:    */   {
/*  73:102 */     return this.idConfiguracionExtractoBancario;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdConfiguracionExtractoBancario(int idConfiguracionExtractoBancario)
/*  77:    */   {
/*  78:106 */     this.idConfiguracionExtractoBancario = idConfiguracionExtractoBancario;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdOrganizacion()
/*  82:    */   {
/*  83:110 */     return this.idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdOrganizacion(int idOrganizacion)
/*  87:    */   {
/*  88:114 */     this.idOrganizacion = idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdSucursal()
/*  92:    */   {
/*  93:118 */     return this.idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdSucursal(int idSucursal)
/*  97:    */   {
/*  98:122 */     this.idSucursal = idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 102:    */   {
/* 103:126 */     return this.cuentaBancariaOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 107:    */   {
/* 108:130 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getDescripcion()
/* 112:    */   {
/* 113:134 */     return this.descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDescripcion(String descripcion)
/* 117:    */   {
/* 118:138 */     this.descripcion = descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isActivo()
/* 122:    */   {
/* 123:142 */     return this.activo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setActivo(boolean activo)
/* 127:    */   {
/* 128:146 */     this.activo = activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isPredeterminado()
/* 132:    */   {
/* 133:150 */     return this.predeterminado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setPredeterminado(boolean predeterminado)
/* 137:    */   {
/* 138:154 */     this.predeterminado = predeterminado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<DetalleConfiguracionExtractoBancario> getListaDetalleConfiguracionExtractoBancario()
/* 142:    */   {
/* 143:158 */     return this.listaDetalleConfiguracionExtractoBancario;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setListaDetalleConfiguracionExtractoBancario(List<DetalleConfiguracionExtractoBancario> listaDetalleConfiguracionExtractoBancario)
/* 147:    */   {
/* 148:162 */     this.listaDetalleConfiguracionExtractoBancario = listaDetalleConfiguracionExtractoBancario;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Integer getColumnaOperacion()
/* 152:    */   {
/* 153:166 */     return this.columnaOperacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setColumnaOperacion(Integer columnaOperacion)
/* 157:    */   {
/* 158:170 */     this.columnaOperacion = columnaOperacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Integer getColumnaMonto()
/* 162:    */   {
/* 163:174 */     return this.columnaMonto;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setColumnaMonto(Integer columnaMonto)
/* 167:    */   {
/* 168:178 */     this.columnaMonto = columnaMonto;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Integer getColumnaFecha()
/* 172:    */   {
/* 173:182 */     return this.columnaFecha;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setColumnaFecha(Integer columnaFecha)
/* 177:    */   {
/* 178:186 */     this.columnaFecha = columnaFecha;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getFormatoFecha()
/* 182:    */   {
/* 183:190 */     return this.formatoFecha;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setFormatoFecha(String formatoFecha)
/* 187:    */   {
/* 188:194 */     this.formatoFecha = formatoFecha;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ConfiguracionExtractoBancario
 * JD-Core Version:    0.7.0.1
 */