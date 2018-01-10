/*   1:    */ package com.asinfo.as2.entities.amortizacion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.Date;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.validation.constraints.Digits;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="detalle_amortizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_amortizacion"})})
/*  30:    */ public class DetalleAmortizacion
/*  31:    */   extends EntidadBase
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="detalle_amortizacion", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_amortizacion")
/*  38:    */   @Column(name="id_detalle_amortizacion")
/*  39:    */   private int idDetalleAmortizacion;
/*  40:    */   @Column(name="id_organizacion")
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Column(name="id_sucursal")
/*  43:    */   private int idSucursal;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_amortizacion", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Amortizacion amortizacion;
/*  48:    */   @Temporal(TemporalType.DATE)
/*  49:    */   @Column(name="fecha_vencimiento", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private Date fechaVencimiento;
/*  52:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  53:    */   @NotNull
/*  54:    */   @Digits(integer=12, fraction=2)
/*  55:    */   @Min(0L)
/*  56:    */   private BigDecimal valor;
/*  57:    */   @Column(name="estado", nullable=false)
/*  58:    */   @Enumerated(EnumType.ORDINAL)
/*  59:    */   private Estado estado;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  62:    */   private Asiento asiento;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  65:    */   private InterfazContableProceso interfazContableProceso;
/*  66:    */   
/*  67:    */   public int getId()
/*  68:    */   {
/*  69: 97 */     return this.idDetalleAmortizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdDetalleAmortizacion()
/*  73:    */   {
/*  74:104 */     return this.idDetalleAmortizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdDetalleAmortizacion(int idDetalleAmortizacion)
/*  78:    */   {
/*  79:112 */     this.idDetalleAmortizacion = idDetalleAmortizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:119 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:127 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94:134 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:142 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Amortizacion getAmortizacion()
/* 103:    */   {
/* 104:149 */     return this.amortizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setAmortizacion(Amortizacion amortizacion)
/* 108:    */   {
/* 109:157 */     this.amortizacion = amortizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Date getFechaVencimiento()
/* 113:    */   {
/* 114:164 */     return this.fechaVencimiento;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 118:    */   {
/* 119:172 */     this.fechaVencimiento = fechaVencimiento;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public BigDecimal getValor()
/* 123:    */   {
/* 124:179 */     return this.valor;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setValor(BigDecimal valor)
/* 128:    */   {
/* 129:187 */     this.valor = valor;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Estado getEstado()
/* 133:    */   {
/* 134:194 */     return this.estado;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setEstado(Estado estado)
/* 138:    */   {
/* 139:202 */     this.estado = estado;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Asiento getAsiento()
/* 143:    */   {
/* 144:209 */     return this.asiento;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setAsiento(Asiento asiento)
/* 148:    */   {
/* 149:217 */     this.asiento = asiento;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public InterfazContableProceso getInterfazContableProceso()
/* 153:    */   {
/* 154:224 */     return this.interfazContableProceso;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 158:    */   {
/* 159:232 */     this.interfazContableProceso = interfazContableProceso;
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.amortizacion.DetalleAmortizacion
 * JD-Core Version:    0.7.0.1
 */