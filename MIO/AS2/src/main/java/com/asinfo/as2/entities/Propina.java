/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="propina", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_pago_rol"})})
/*  24:    */ public class Propina
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="propina", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="propina")
/*  31:    */   @Column(name="id_propina")
/*  32:    */   private int idPropina;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @NotNull
/*  38:    */   @Column(name="valor", nullable=false)
/*  39:    */   private BigDecimal valor;
/*  40:    */   @Temporal(TemporalType.DATE)
/*  41:    */   @Column(name="fecha", nullable=true)
/*  42:    */   private Date fecha;
/*  43:    */   @Enumerated(EnumType.STRING)
/*  44:    */   @Column(name="estado", length=50, nullable=true)
/*  45:    */   private Estado estado;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_pago_rol", nullable=true)
/*  48:    */   private PagoRol pagoRol;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_pago_rol_dias_trabajados", nullable=true)
/*  51:    */   private PagoRol pagoRolDiasTrabajados;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 84 */     return this.idPropina;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdPropina()
/*  59:    */   {
/*  60: 88 */     return this.idPropina;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdPropina(int idPropina)
/*  64:    */   {
/*  65: 92 */     this.idPropina = idPropina;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 96 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75:100 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:104 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:108 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public BigDecimal getValor()
/*  89:    */   {
/*  90:112 */     return this.valor;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setValor(BigDecimal valor)
/*  94:    */   {
/*  95:116 */     this.valor = valor;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Date getFecha()
/*  99:    */   {
/* 100:120 */     return this.fecha;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setFecha(Date fecha)
/* 104:    */   {
/* 105:124 */     this.fecha = fecha;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Estado getEstado()
/* 109:    */   {
/* 110:128 */     return this.estado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setEstado(Estado estado)
/* 114:    */   {
/* 115:132 */     this.estado = estado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public PagoRol getPagoRol()
/* 119:    */   {
/* 120:136 */     return this.pagoRol;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPagoRol(PagoRol pagoRol)
/* 124:    */   {
/* 125:140 */     this.pagoRol = pagoRol;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public PagoRol getPagoRolDiasTrabajados()
/* 129:    */   {
/* 130:144 */     return this.pagoRolDiasTrabajados;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPagoRolDiasTrabajados(PagoRol pagoRolDiasTrabajados)
/* 134:    */   {
/* 135:148 */     this.pagoRolDiasTrabajados = pagoRolDiasTrabajados;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Propina
 * JD-Core Version:    0.7.0.1
 */