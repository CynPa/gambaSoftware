/*   1:    */ package com.asinfo.as2.entities.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="configuracion_carga_ticket")
/*  21:    */ public class ConfiguracionCargaTicket
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="configuracion_carga_ticket", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="configuracion_carga_ticket")
/*  29:    */   @Column(name="id_configuracion_carga_ticket", unique=true, nullable=false)
/*  30:    */   private int idConfiguracionCargaTicket;
/*  31:    */   @Column(name="nombre", nullable=false, length=50)
/*  32:    */   @Size(max=50)
/*  33:    */   private String nombre;
/*  34:    */   @Column(name="nombre_etiqueta", nullable=false, length=50)
/*  35:    */   @Size(max=50)
/*  36:    */   private String nombreEtiqueta;
/*  37:    */   @Column(name="orden", nullable=true)
/*  38:    */   private int orden;
/*  39:    */   @Column(name="monto", nullable=true)
/*  40:    */   private String valor;
/*  41:    */   @Column(name="criterio", nullable=true, length=50)
/*  42:    */   @Size(max=50)
/*  43:    */   private String criterio;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_cuenta_contable_tkt_1", nullable=true)
/*  46:    */   private CuentaContable cuentaContableTKT1;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_cuenta_contable_tkt_2", nullable=true)
/*  49:    */   private CuentaContable cuentaContableTKT2;
/*  50:    */   @Column(name="id_organizacion", nullable=false)
/*  51:    */   private int idOrganizacion;
/*  52:    */   @Column(name="id_sucursal", nullable=false)
/*  53:    */   private int idSucursal;
/*  54:    */   @Transient
/*  55: 67 */   private int indice = -1;
/*  56:    */   @Column(name="referencia", nullable=true, length=50)
/*  57:    */   @Size(max=50)
/*  58:    */   private String referencia;
/*  59:    */   @Column(name="referencia2", nullable=true, length=50)
/*  60:    */   @Size(max=50)
/*  61:    */   private String referencia2;
/*  62:    */   
/*  63:    */   public int getIdConfiguracionCargaTicket()
/*  64:    */   {
/*  65: 79 */     return this.idConfiguracionCargaTicket;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdConfiguracionCargaTicket(int idConfiguracionCargaTicket)
/*  69:    */   {
/*  70: 83 */     this.idConfiguracionCargaTicket = idConfiguracionCargaTicket;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getNombre()
/*  74:    */   {
/*  75: 87 */     return this.nombre;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setNombre(String nombre)
/*  79:    */   {
/*  80: 91 */     this.nombre = nombre;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getOrden()
/*  84:    */   {
/*  85: 95 */     return this.orden;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setOrden(int orden)
/*  89:    */   {
/*  90: 99 */     this.orden = orden;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public CuentaContable getCuentaContableTKT1()
/*  94:    */   {
/*  95:103 */     return this.cuentaContableTKT1;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setCuentaContableTKT1(CuentaContable cuentaContableTKT1)
/*  99:    */   {
/* 100:107 */     this.cuentaContableTKT1 = cuentaContableTKT1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public CuentaContable getCuentaContableTKT2()
/* 104:    */   {
/* 105:111 */     return this.cuentaContableTKT2;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setCuentaContableTKT2(CuentaContable cuentaContableTKT2)
/* 109:    */   {
/* 110:115 */     this.cuentaContableTKT2 = cuentaContableTKT2;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getIndice()
/* 114:    */   {
/* 115:119 */     return this.indice;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setIndice(int indice)
/* 119:    */   {
/* 120:123 */     this.indice = indice;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getId()
/* 124:    */   {
/* 125:128 */     return this.idConfiguracionCargaTicket;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getIdOrganizacion()
/* 129:    */   {
/* 130:132 */     return this.idOrganizacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIdOrganizacion(int idOrganizacion)
/* 134:    */   {
/* 135:136 */     this.idOrganizacion = idOrganizacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getIdSucursal()
/* 139:    */   {
/* 140:140 */     return this.idSucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdSucursal(int idSucursal)
/* 144:    */   {
/* 145:144 */     this.idSucursal = idSucursal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getNombreEtiqueta()
/* 149:    */   {
/* 150:148 */     return this.nombreEtiqueta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setNombreEtiqueta(String nombreEtiqueta)
/* 154:    */   {
/* 155:152 */     this.nombreEtiqueta = nombreEtiqueta;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getValor()
/* 159:    */   {
/* 160:156 */     return this.valor;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setValor(String valor)
/* 164:    */   {
/* 165:160 */     this.valor = valor;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getCriterio()
/* 169:    */   {
/* 170:164 */     return this.criterio;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCriterio(String criterio)
/* 174:    */   {
/* 175:168 */     this.criterio = criterio;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getReferencia()
/* 179:    */   {
/* 180:172 */     return this.referencia;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setReferencia(String referencia)
/* 184:    */   {
/* 185:176 */     this.referencia = referencia;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getReferencia2()
/* 189:    */   {
/* 190:180 */     return this.referencia2;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setReferencia2(String referencia2)
/* 194:    */   {
/* 195:184 */     this.referencia2 = referencia2;
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.aerolineas.ConfiguracionCargaTicket
 * JD-Core Version:    0.7.0.1
 */