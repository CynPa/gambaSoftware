/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="historico_depreciacion")
/*  26:    */ public class HistoricoDepreciacion
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="historico_depreciacion", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="historico_depreciacion")
/*  33:    */   @Column(name="id_historico_depreciacion")
/*  34:    */   private int idHistoricoDepreciacion;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  39:    */   private Sucursal sucursal;
/*  40:    */   @Column(name="estado", nullable=false)
/*  41:    */   @Enumerated(EnumType.ORDINAL)
/*  42:    */   private Estado estado;
/*  43:    */   @Column(name="anio", nullable=false)
/*  44:    */   @Min(0L)
/*  45:    */   private int anio;
/*  46:    */   @Column(name="mes", nullable=false)
/*  47:    */   @Min(0L)
/*  48:    */   private int mes;
/*  49:    */   @OneToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_asiento_fiscal", nullable=true)
/*  51:    */   private Asiento asientoFiscal;
/*  52:    */   @OneToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_asiento_niif", nullable=true)
/*  54:    */   private Asiento asientoNIIF;
/*  55:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="historicoDepreciacion")
/*  56: 88 */   private List<DetalleDepreciacion> listaDetalleDepreciacion = new ArrayList();
/*  57:    */   @Transient
/*  58:    */   private String traNombreMes;
/*  59:    */   
/*  60:    */   public int getIdHistoricoDepreciacion()
/*  61:    */   {
/*  62:111 */     return this.idHistoricoDepreciacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdHistoricoDepreciacion(int idHistoricoDepreciacion)
/*  66:    */   {
/*  67:121 */     this.idHistoricoDepreciacion = idHistoricoDepreciacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:130 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:140 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Sucursal getSucursal()
/*  81:    */   {
/*  82:149 */     return this.sucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setSucursal(Sucursal sucursal)
/*  86:    */   {
/*  87:159 */     this.sucursal = sucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Estado getEstado()
/*  91:    */   {
/*  92:168 */     return this.estado;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setEstado(Estado estado)
/*  96:    */   {
/*  97:178 */     this.estado = estado;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getAnio()
/* 101:    */   {
/* 102:187 */     return this.anio;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setAnio(int anio)
/* 106:    */   {
/* 107:197 */     this.anio = anio;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Asiento getAsientoFiscal()
/* 111:    */   {
/* 112:206 */     return this.asientoFiscal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setAsientoFiscal(Asiento asientoFiscal)
/* 116:    */   {
/* 117:216 */     this.asientoFiscal = asientoFiscal;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Asiento getAsientoNIIF()
/* 121:    */   {
/* 122:225 */     return this.asientoNIIF;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setAsientoNIIF(Asiento asientoNIIF)
/* 126:    */   {
/* 127:235 */     this.asientoNIIF = asientoNIIF;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<DetalleDepreciacion> getListaDetalleDepreciacion()
/* 131:    */   {
/* 132:244 */     return this.listaDetalleDepreciacion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaDetalleDepreciacion(List<DetalleDepreciacion> listaDetalleDepreciacion)
/* 136:    */   {
/* 137:254 */     this.listaDetalleDepreciacion = listaDetalleDepreciacion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getId()
/* 141:    */   {
/* 142:264 */     return this.idHistoricoDepreciacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getMes()
/* 146:    */   {
/* 147:273 */     return this.mes;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setMes(int mes)
/* 151:    */   {
/* 152:283 */     this.mes = mes;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getTraNombreMes()
/* 156:    */   {
/* 157:292 */     this.traNombreMes = FuncionesUtiles.nombreMes(this.mes - 1);
/* 158:293 */     return this.traNombreMes;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setTraNombreMes(String traNombreMes)
/* 162:    */   {
/* 163:303 */     this.traNombreMes = traNombreMes;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.HistoricoDepreciacion
 * JD-Core Version:    0.7.0.1
 */