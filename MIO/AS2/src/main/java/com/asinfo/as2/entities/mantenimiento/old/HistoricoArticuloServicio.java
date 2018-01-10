/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="historico_articulo_servicio")
/*  21:    */ public class HistoricoArticuloServicio
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 3608488437402238079L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="historico_articulo_servicio", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="historico_articulo_servicio")
/*  28:    */   @Column(name="id_historico_articulo_servicio")
/*  29:    */   private int idHistoricoServicio;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_articulo_servicio_padre", nullable=true)
/*  32:    */   private ArticuloServicio articuloServicioPadre;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_articulo_servicio_hijo", nullable=true)
/*  35:    */   private ArticuloServicio articuloServicioHijo;
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   @Column(name="fecha_desde", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private Date fechaDesde;
/*  40:    */   @Temporal(TemporalType.TIME)
/*  41:    */   @Column(name="hora_desde", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Date horaDesde;
/*  44:    */   @Temporal(TemporalType.TIMESTAMP)
/*  45:    */   @Column(name="fecha_hasta", nullable=true)
/*  46:    */   private Date fechaHasta;
/*  47:    */   @Temporal(TemporalType.TIME)
/*  48:    */   @Column(name="hora_hasta", nullable=true)
/*  49:    */   private Date horaHasta;
/*  50:    */   
/*  51:    */   public int getIdHistoricoServicio()
/*  52:    */   {
/*  53:107 */     return this.idHistoricoServicio;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdHistoricoServicio(int idHistoricoServicio)
/*  57:    */   {
/*  58:117 */     this.idHistoricoServicio = idHistoricoServicio;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ArticuloServicio getArticuloServicioPadre()
/*  62:    */   {
/*  63:126 */     return this.articuloServicioPadre;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setArticuloServicioPadre(ArticuloServicio articuloServicioPadre)
/*  67:    */   {
/*  68:136 */     this.articuloServicioPadre = articuloServicioPadre;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ArticuloServicio getArticuloServicioHijo()
/*  72:    */   {
/*  73:145 */     return this.articuloServicioHijo;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setArticuloServicioHijo(ArticuloServicio articuloServicioHijo)
/*  77:    */   {
/*  78:155 */     this.articuloServicioHijo = articuloServicioHijo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Date getFechaDesde()
/*  82:    */   {
/*  83:164 */     return this.fechaDesde;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setFechaDesde(Date fechaDesde)
/*  87:    */   {
/*  88:174 */     this.fechaDesde = fechaDesde;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Date getFechaHasta()
/*  92:    */   {
/*  93:183 */     return this.fechaHasta;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setFechaHasta(Date fechaHasta)
/*  97:    */   {
/*  98:193 */     this.fechaHasta = fechaHasta;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Date getHoraDesde()
/* 102:    */   {
/* 103:202 */     return this.horaDesde;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setHoraDesde(Date horaDesde)
/* 107:    */   {
/* 108:212 */     this.horaDesde = horaDesde;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Date getHoraHasta()
/* 112:    */   {
/* 113:221 */     return this.horaHasta;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setHoraHasta(Date horaHasta)
/* 117:    */   {
/* 118:231 */     this.horaHasta = horaHasta;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:241 */     return this.idHistoricoServicio;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio
 * JD-Core Version:    0.7.0.1
 */